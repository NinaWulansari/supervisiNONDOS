package com.wahanaartha.supervisionline.Activities

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.multidex.MultiDex
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.wahanaartha.supervisionline.Connect.API
import com.wahanaartha.supervisionline.Connect.API.baseURLPicasso
import com.wahanaartha.supervisionline.Model.InsertSupervisi
import com.wahanaartha.supervisionline.Model.LoginUser
import com.wahanaartha.supervisionline.R
import com.wahanaartha.supervisionline.Utils.FileUtils
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    lateinit var type: String
    internal var idParent: String? = null
    lateinit var status_pica: String
    internal var path: Uri? = null
    internal var currentPosition = 0
    internal var remark = "remark"
    var file: Uri? = null

    //LOCATION
    private val TAG = "MainActivity"
    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    lateinit var mLocation: Location
    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    lateinit var locationManager: LocationManager

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

        txt_latitude.text = "" + location.latitude
        txt_longitude.text = "" + location.longitude
    }

    override fun onConnected(p0: Bundle?) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }


        startLocationUpdates()

        val fusedLocationProviderClient:
                FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation
                .addOnSuccessListener(this, OnSuccessListener<Location> { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        mLocation = location
                        txt_latitude.text = "" + mLocation.latitude
                        txt_longitude.text = "" + mLocation.longitude
                    }
                })
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        titleSearch!!.text = intent.getStringExtra(NAME)
        titleSearch!!.setTextColor(Color.WHITE)

        val mClickListener = View.OnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            file = Uri.fromFile(getOutputMediaFile())
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file)
            startActivityForResult(intent, 100)
        }

        question!!.text = intent.getStringExtra(TITLE)
        idParent = intent.getStringExtra(ID_PARENT)
        type = intent.getStringExtra(TYPE)

        //LOC
        MultiDex.install(this)

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkLocation()

        button_save_history.setOnClickListener {
            doUpload()
        }

        if (intent.getStringExtra(EXIST_GOOD) == "1") {
            good?.visibility = View.VISIBLE
        }
        if (intent.getStringExtra(EXIST_NOT_GOOD) == "1") {
            not_good?.visibility = View.VISIBLE
        }
        if (intent.getStringExtra(NOT_EXIST) == "1") {
            not_exist?.visibility = View.VISIBLE
        }
        if (intent.getStringExtra(N_A) == "1") {
            na?.visibility = View.VISIBLE
        }

        Picasso.with(this@HistoryActivity)
                .load(baseURLPicasso + intent.getStringExtra(PIC))
                .into(images)

        notes!!.setText(intent.getStringExtra(NOTES))
        if (intent.getStringExtra(ANSWER) == "1") {
            good.isChecked = true
        } else if (intent.getStringExtra(ANSWER) == "2") {
            not_good.isChecked = true
        } else if (intent.getStringExtra(ANSWER) == "3") {
            not_exist.isChecked = true
        } else if (intent.getStringExtra(ANSWER) == "4") {
            na.isChecked = true
        }

        if (intent.getStringExtra(TYPE) == "NON DOS"){

            if (intent.getStringExtra(ANSWER) != null){

                good.isSelected = false
                good.isClickable = false

                not_good.isSelected = false
                not_good.isClickable = false

                not_exist.isSelected = false
                not_exist.isClickable = false

                na.isSelected = false
                na.isClickable = false

                notes.isClickable = false
                notes.isEnabled = false
                camera.isClickable = false
                camera.isEnabled = false

                button_save_history.isEnabled = false
                button_save_history.isClickable = false
                button_save_history.setBackgroundColor(resources.getColor(R.color.grey_400))

            }

        }

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

        camera.setOnClickListener(mClickListener)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            camera.isEnabled = false
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                camera.isEnabled = true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                images.setImageURI(file)
            }
        }
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
        return File(mediaStorageDir.path + File.separator +
                "SUPERVISI_" + imageDir + ".jpg")
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        //Log.e("in","conversion"+contentURI.getPath());
        val path: String
        val cursor = this.contentResolver
                .query(contentURI, null, null, null, null)
        if (cursor == null)
            path = contentURI.path
        else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            path = cursor.getString(idx)

        }
        if (cursor != null)
            cursor.close()
        return path
    }

    val isValid: Boolean
        get() {
            var valid = true
            val remark = remark
            val notes = notes!!.text.toString()
            val image = file

            if (remark == null) {
                Toast.makeText(applicationContext, "You must answer it",
                        Toast.LENGTH_SHORT).show()
                valid = false
            }

            if (image == null) {
                Toast.makeText(applicationContext, "You must take a picture",
                        Toast.LENGTH_SHORT).show()
                valid = false
            }

            if (notes == null) {
                Toast.makeText(applicationContext, "You must write a notes",
                        Toast.LENGTH_SHORT).show()
                valid = false
            }

            return valid
        }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun doUpload() {
        if (isValid) {
            val progressDialog = ProgressDialog(this@HistoryActivity)
            progressDialog.setMessage("Inserting...")
            progressDialog.show()
            val saveUser = Gson().fromJson<LoginUser>(this@HistoryActivity.getSharedPreferences(LoginActivity.MY_LOGIN_PREF, android.content.Context.MODE_PRIVATE).getString(LoginActivity.MY_LOGIN_PREF_KEY, ""), LoginUser::class.java)

            val file = FileUtils.getFile(this, file)
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("pic", file.name, reqFile)


            // add another part within the multipart request
            val pica = notes.text.toString()
            val id_parent = intent.getStringExtra(ID_PARENT)
            val id_item = intent.getStringExtra(ID_ITEM)
            val lati = mLocation.latitude
            val longi = mLocation.longitude
            val no_dlr = intent.getStringExtra(NO_DLR)
//            val nama_dlr = intent.getStringExtra(NAMA_DLR)
            val jawaban = remark
            val type_soal = intent.getStringExtra(TYPE)
            val nama_dlr1 = intent.getStringExtra(NAMA_DLR)
            val data_create_by = saveUser.id
            val tgl_supervisi1 = intent.getStringExtra(TGL_SURVEY)

//            val id = RequestBody.create(MediaType.parse("text/plain"), id_item)
            val pi = RequestBody.create(MediaType.parse("text/plain"), pica)
            val main_item = RequestBody.create(MediaType.parse("text/plain"), id_parent)
            val item = RequestBody.create(MediaType.parse("text/plain"), id_item)
            val latitude = RequestBody.create(MediaType.parse("text/plain"), lati.toString())
            val longitude = RequestBody.create(MediaType.parse("text/plain"), longi.toString())
            val noDlr = RequestBody.create(MediaType.parse("text/plain"), no_dlr)
            val remark = RequestBody.create(MediaType.parse("text/plain"), jawaban)
            val type = RequestBody.create(MediaType.parse("text/plain"), type_soal)
            val nama_dlr = RequestBody.create(MediaType.parse("text/plain"), nama_dlr1)
            val tgl_supervisi = RequestBody.create(MediaType.parse("text/plain"), tgl_supervisi1)


            val create_by = RequestBody.create(MediaType.parse("text/plain"), data_create_by)
            if (jawaban != "1") {
                status_pica = "0"
            } else {
                status_pica = "Done"
            }

            val status_pi = RequestBody.create(MediaType.parse("text/plain"), status_pica)

//            if (type_soal.equals("DOS")){
//                if (intent.getStringExtra(PI) == null){
//                    API.addHistory(pi, main_item, item, latitude, longitude, noDlr, remark, create_by, status_pi, type, nama_dlr, body).enqueue(object : Callback<InsertSupervisi> {
//                        override fun onResponse(call: Call<InsertSupervisi>?, response: Response<InsertSupervisi>?) {
//                            if (response!!.body()!!.error == false) {
//                                Log.i(TAG, "INSERT : " + response.body())
//                                progressDialog.dismiss()
//                                val fdelete = File(file!!.path)
//                                if (fdelete.exists()) {
//                                    if (fdelete.delete()) {
//                                        Log.i("image", "delete succsess: " + file.path)
//                                    } else {
//                                        Log.i("image", "delete failed: " + file.path)
//                                    }
//                                }
//                                Toast.makeText(this@HistoryActivity, "Succes", Toast.LENGTH_SHORT).show()
//                                finish()
//                            } else {
//                                Toast.makeText(this@HistoryActivity, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<InsertSupervisi>?, t: Throwable?) {
//                            Toast.makeText(this@HistoryActivity, "Connection Error", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                } else {
//                    API.editHistory(id, pi, remark, create_by, body).enqueue(object : Callback<InsertSupervisi> {
//                        override fun onResponse(call: Call<InsertSupervisi>?, response: Response<InsertSupervisi>?) {
//                            if (response!!.body()!!.error == false) {
//                                Log.i(TAG, "INSERT : " + response.body())
//                                progressDialog.dismiss()
//                                val fdelete = File(file!!.path)
//                                if (fdelete.exists()) {
//                                    if (fdelete.delete()) {
//                                        Log.i("image", "delete succsess: " + file.path)
//                                    } else {
//                                        Log.i("image", "delete failed: " + file.path)
//                                    }
//                                }
//                                Toast.makeText(this@HistoryActivity, "Succes", Toast.LENGTH_SHORT).show()
//                                finish()
//                            } else {
//                                Toast.makeText(this@HistoryActivity, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<InsertSupervisi>?, t: Throwable?) {
//                            Toast.makeText(this@HistoryActivity, "Connection Error", Toast.LENGTH_SHORT).show()
//                        }
//                    })
//                }
//            } else {
            API.addHistory(pi, main_item, item, latitude, longitude, noDlr, remark, create_by, status_pi, type, nama_dlr, tgl_supervisi, body).enqueue(object : Callback<InsertSupervisi> {
                override fun onResponse(call: Call<InsertSupervisi>?, response: Response<InsertSupervisi>?) {
                    if (response!!.body()!!.error == false) {
                        Log.i(TAG, "INSERT : " + response.body())
                        progressDialog.dismiss()
                        val fdelete = File(file!!.path)
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                Log.i("image", "delete succsess: " + file.path)
                            } else {
                                Log.i("image", "delete failed: " + file.path)
                            }
                        }
                        Toast.makeText(this@HistoryActivity, "Succes", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@HistoryActivity, "" + response.body()!!.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<InsertSupervisi>?, t: Throwable?) {
                    Toast.makeText(this@HistoryActivity, "Connection Error", Toast.LENGTH_SHORT).show()
                }
            })
//            }


        }

    }

    //LOC
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

    fun getCurrentPosition(): Int {
        return currentPosition
    }

    companion object {
        val TGL_SURVEY = "tgl_survey"
        val ID_PARENT = "id"
        val ID_ITEM = "id_item"
        val TITLE = "title"
        val TYPE = "type"
        val LAST_ROOT = "last_root"
        val EXIST_GOOD = "exist_good"
        val EXIST_NOT_GOOD = "exist_not_good"
        val NOT_EXIST = "not_exist"
        val N_A = "n_a"
        val PI = "pi"
        val CA = "ca"
        val PIC = "PIC"
        val NEW_DEADLINE = "new_deadline"
        val DEADLINE = "deadline"
        val REASON = "reason"
        val NOTES = "notes"
        val ANSWER = "answer"
        val NO_DLR = "no_dlr"
        val NAME = "name"
        val NAMA_DLR = "nama_dlr"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


