package com.wahanaartha.supervisionline.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Model.InsertSupervisi
import com.wahanaartha.supervisionline.Model.ListQuestionsModel
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.FileUtils
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class QuizActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    lateinit var quesList: List<ListQuestionsModel>
    internal var qid = 0
    lateinit var currentQ: ListQuestionsModel
    var file: Uri? = null
    internal var type: String? = null
    lateinit var namaDlr: String
    lateinit var parent_id: String
    lateinit var no_dlr: String
    lateinit var status_pica: String
    private val TAG = "MainActivity"
    internal var remark: String? = null
    lateinit var id_question: String
    lateinit var prefs: SharedPreferences
    lateinit var share_prefs: SharedPreferences
    internal var typeDlr: String? = null

    //loc
    lateinit var locationManager: LocationManager
    lateinit var mGoogleApiClient: GoogleApiClient
    var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    var mLocationRequest: LocationRequest? = null
    val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        setSupportActionBar(toolbar)
        titleSearch!!.text = intent.getStringExtra(NAME)
        titleSearch!!.setTextColor(Color.WHITE)

        prefs = getSharedPreferences("X", Context.MODE_PRIVATE)
        type = prefs.getString("type_supervisi", "")
        typeDlr = prefs.getString("type_dealer", "")

        titleSearch!!.text = prefs.getString("nama_kategori", "")
        titleSearch!!.setTextColor(Color.WHITE)
        parent_id = prefs.getString("id_kategori", "")
        namaDlr = prefs.getString("nama_dealer", "")
        no_dlr = prefs.getString("no_dlr", "")

//        share_prefs = getSharedPreferences("PREF", Context.MODE_PRIVATE)
//        currentRemark = share_prefs.getInt("current_remark", 0)

        API.getQuestions(parent_id, type, typeDlr).enqueue(object : Callback<List<ListQuestionsModel>> {
            @SuppressLint("NewApi")
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<List<ListQuestionsModel>>, response: Response<List<ListQuestionsModel>>) {
                Log.i(TAG, "Connection failed. Error: " + response.body())
                if (response.code() == 200) {
                    quesList = response.body()!!
                    Log.i("Supervisi", "questions: " + Gson().toJson(quesList))
                    currentQ = quesList[qid]
                    setQuestionView()
                    prev_button.visibility = View.GONE
                    next_button.setOnClickListener {
//                        prev_button.visibility = View.VISIBLE
                        if (qid < quesList.size - 1) {
                            if (type.equals("DOS")) {
                                if (remark == null) {
                                    val dialog = AlertDialog.Builder(this@QuizActivity)
                                    dialog.setTitle("Skip Question")
                                            .setMessage("You not answer the question\n You want to skip the question?")
                                            .setPositiveButton("Yes", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                                                setQuestionView()
//                                                next()
                                            })
                                            .setNegativeButton("No", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                                            })
                                    dialog.show()
                                } else {
                                    doUpload()
//                                    next()
                                }
                            } else {
                                //NON DOS
                                if (remark == null) {
                                    Toast.makeText(this@QuizActivity, "You must answer it!", Toast.LENGTH_SHORT).show()
                                } else {
                                    doUpload()
                                }
                            }
                        } else {
                            doUpload2()
                            val intent = Intent(this@QuizActivity, ResultActivity::class.java)
                            startActivity(intent)
                        }
                    }

//                    prev_button.setOnClickListener {
//                        currentQ = quesList[qid - 1]
//                    }
                } else {
                    Toast.makeText(this@QuizActivity, "Please check your connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ListQuestionsModel>>?, t: Throwable?) {
                Toast.makeText(this@QuizActivity, "Please check your connection", Toast.LENGTH_LONG).show()
            }

        })

        radio_group.setOnCheckedChangeListener({ group, checkedId ->
            if (good.isChecked) {
                remark = "1"
            } else if (not_good.isChecked) {
                remark = "2"
            } else if (not_exist.isChecked) {
                remark = "3"
            } else if (na.isChecked) {
                remark = "4"
            }
        })

        //LOC
        MultiDex.install(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkLocation()

        btn_camera.setOnClickListener(mClickListener)
    }

    //onbackpress
    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this@QuizActivity)
        dialog.setTitle("NON DOS")
                .setMessage("Anda harus menyelesaikan semua pertanyaan!")
                .setNegativeButton("OK", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    //location
    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {

        Log.i(TAG, "Connection Suspended")
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.errorCode)
    }

    override fun onLocationChanged(location: Location) {

        Log.i(TAG, "Latitude : " + location.latitude)
        Log.i(TAG, "Longitude : " + location.longitude)
    }

    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        startLocationUpdates()

        var fusedLocationProviderClient:
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location
                        Log.i(TAG, "Latitude : " + mLocation.latitude)
                        Log.i(TAG, "Longitude : " + mLocation.longitude)
                    }
                })
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
                .setPositiveButton("Location Settings", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    protected fun startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this)
    }

    fun isValid(): Boolean {
        var valid: Boolean = true
        val note = notes.text.toString()

        if (remark == null) {
            Toast.makeText(applicationContext, "You must answer it",
                    Toast.LENGTH_SHORT).show()
            valid = false
        }

        if (file == null) {
            Toast.makeText(applicationContext, "You must take a picture",
                    Toast.LENGTH_SHORT).show()
            valid = false
        }

        if (note.isEmpty()) {
            Toast.makeText(applicationContext, "You must write a notes",
                    Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun doUpload() {
        if (!isValid()) {
            return
        }

        val progressDialog = ProgressDialog(this@QuizActivity)
        progressDialog.setMessage("Inserting...")
        progressDialog.show()
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)

        val saveUser = Gson().fromJson<LoginUser>(this@QuizActivity.getSharedPreferences(LoginActivity.MY_LOGIN_PREF, android.content.Context.MODE_PRIVATE).getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)
        val file2 = FileUtils.getFile(this, file)
        try {
            val bitmap = BitmapFactory.decodeFile(file2.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 17, FileOutputStream(file2))
        } catch (t: Throwable) {
            Log.e("ERROR", "Error compressing file." + t.toString())
            t.printStackTrace()
        }
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file2)
        val body = MultipartBody.Part.createFormData("pic", file2.name, reqFile)

        // add another part within the multipart request
        val pi = RequestBody.create(MediaType.parse("text/plain"), notes!!.text.toString())
        val main_item = RequestBody.create(MediaType.parse("text/plain"), parent_id)
        val item = RequestBody.create(MediaType.parse("text/plain"), id_question)
        val latitude = RequestBody.create(MediaType.parse("text/plain"), mLocation.latitude.toString())
        val longitude = RequestBody.create(MediaType.parse("text/plain"), mLocation.longitude.toString())
        val noDlr = RequestBody.create(MediaType.parse("text/plain"), no_dlr)
        val jawaban = RequestBody.create(MediaType.parse("text/plain"), remark)
        val tipe = RequestBody.create(MediaType.parse("text/plain"), type)
        val create_by = RequestBody.create(MediaType.parse("text/plain"), saveUser.id)
        if (remark != "1") {
            status_pica = "0"
        } else {
            status_pica = "Done"
        }
        val status_pi = RequestBody.create(MediaType.parse("text/plain"), status_pica)
        val nama_dlr = RequestBody.create(MediaType.parse("text/plain"), namaDlr)

        API.addSupervisi(pi, main_item, item, latitude, longitude, noDlr, jawaban, create_by, status_pi, tipe, nama_dlr, body).enqueue(object : Callback<InsertSupervisi> {
            override fun onResponse(call: Call<InsertSupervisi>, response: Response<InsertSupervisi>) {
                if (response.code() == 200) {
                    remark = null
                    file = null
                    Log.i(TAG, "INSERT : " + response.body())
                    progressDialog.dismiss()
                    val fdelete = File(file2.path)
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            Log.i("image", "delete succsess: ")
                            callBroadCast()
                        } else {
                            Log.i("image", "delete failed: ")
                        }
                    }
                    Toast.makeText(this@QuizActivity, "Insert Successfully", Toast.LENGTH_SHORT).show()
                    if(currentQ != quesList){
                        qid++
                        currentQ = quesList[qid]
                        setQuestionView()
                        radio_group.clearCheck()
                        images.setImageResource(android.R.color.transparent)
                        notes.text.clear()
                    }
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@QuizActivity, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InsertSupervisi>?, t: Throwable?) {
                progressDialog.dismiss()
                Toast.makeText(this@QuizActivity, "Connection Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun doUpload2() {
        if (!isValid()) {
            return
        }

        val progressDialog = ProgressDialog(this@QuizActivity)
        progressDialog.setMessage("Inserting...")
        progressDialog.show()
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)

        val saveUser = Gson().fromJson<LoginUser>(this@QuizActivity.getSharedPreferences(LoginActivity.MY_LOGIN_PREF, android.content.Context.MODE_PRIVATE).getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

        val file2 = FileUtils.getFile(this, file)
        try {
            val bitmap = BitmapFactory.decodeFile(file2.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 17, FileOutputStream(file2))
        } catch (t: Throwable) {
            Log.e("ERROR", "Error compressing file." + t.toString())
            t.printStackTrace()
        }
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file2)
        val body = MultipartBody.Part.createFormData("pic", file2.name, reqFile)

        // add another part within the multipart request
        val pi = RequestBody.create(MediaType.parse("text/plain"), notes!!.text.toString())
        val main_item = RequestBody.create(MediaType.parse("text/plain"), parent_id)
        val item = RequestBody.create(MediaType.parse("text/plain"), id_question)
        val latitude = RequestBody.create(MediaType.parse("text/plain"), mLocation.latitude.toString())
        val longitude = RequestBody.create(MediaType.parse("text/plain"), mLocation.longitude.toString())
        val noDlr = RequestBody.create(MediaType.parse("text/plain"), no_dlr)
        val jawaban = RequestBody.create(MediaType.parse("text/plain"), remark)
        val tipe = RequestBody.create(MediaType.parse("text/plain"), type)
        val create_by = RequestBody.create(MediaType.parse("text/plain"), saveUser.id)
        if (remark != "1") {
            status_pica = "0"
        } else {
            status_pica = "Done"
        }
        val status_pi = RequestBody.create(MediaType.parse("text/plain"), status_pica)
        val nama_dlr = RequestBody.create(MediaType.parse("text/plain"), namaDlr)

        API.addSupervisi(pi, main_item, item, latitude, longitude, noDlr, jawaban, create_by, status_pi, tipe, nama_dlr, body).enqueue(object : Callback<InsertSupervisi> {
            override fun onResponse(call: Call<InsertSupervisi>, response: Response<InsertSupervisi>) {
                if (response.code() == 200) {
                    Log.i(TAG, "INSERT : " + response.body())
                    progressDialog.dismiss()
                    val fdelete = File(file2.path)
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            Log.i("image", "delete succsess: ")
                            callBroadCast()
                        } else {
                            Log.i("image", "delete failed: ")
                        }
                    }
                    Toast.makeText(this@QuizActivity, "Insert Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this@QuizActivity, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InsertSupervisi>?, t: Throwable?) {
                progressDialog.dismiss()
                Toast.makeText(this@QuizActivity, "Connection Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14")
            MediaScannerConnection.scanFile(this, arrayOf(Environment.getExternalStorageDirectory().toString()), null) { path, uri ->
                /*
                *   (non-Javadoc)
                * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                */
                Log.e("ExternalStorage", "Scanned $path:")
                Log.e("ExternalStorage", "-> uri=$uri")
            }
        } else {
            Log.e("-->", " < 14")
            sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())))
        }
    }

    //CAMERA
    val mClickListener = View.OnClickListener {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        file = Uri.fromFile(getOutputMediaFile())
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
        startActivityForResult(intent, 100)
    }

    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Supervisi")

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val imageDir = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val coba = mediaStorageDir.path + File.separator + "SUPERVISI_" + imageDir + ".jpg"
        return File(coba)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                images.setImageURI(file)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_quiz, menu)
        return true
    }

    private fun setQuestionView() {
        //radio button
        if (currentQ.existGood == "1") {
            good.visibility = View.VISIBLE
        }
        if (currentQ.existNotGood == "1") {
            not_good?.visibility = View.VISIBLE
        }
        if (currentQ.notExist == "1") {
            not_exist?.visibility = View.VISIBLE
        }
        if (currentQ.na == "1") {
            na?.visibility = View.VISIBLE
        }

        header.text = currentQ.title
        id_question = currentQ.id
//        qid++
    }

    companion object {
        val ID_PARENT = "id"
        val NAME = "name"
        val NO_DLR = "no_dlr"
        val NAMA_DLR = "nama_dlr"
        val TYPE_DLR = "type_dlr"
        val TYPE = "type"
    }
}
