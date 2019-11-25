package com.wahanaartha.supervisionline.Activities;

import com.wahanaartha.supervisionline.Model.InsertSupervisi;

public interface QuestionAnswer {
    InsertSupervisi getRemark();

    void setRemark(InsertSupervisi insertSupervisi);

    InsertSupervisi getPic();

    void setPic(InsertSupervisi insertSupervisi);

    InsertSupervisi getNotes();

    void setNotes(InsertSupervisi insertSupervisi);
}