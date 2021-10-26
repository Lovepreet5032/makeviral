package com.prouman.tagmanager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aseemchoudhary on 09/06/18.
 */

public class TagDataPojo {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<TagDataObject> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TagDataObject> getData() {
        return data;
    }

    public void setData(List<TagDataObject> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
    public class Paging {

        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("total_page")
        @Expose
        private Integer totalPage;
        @SerializedName("record_from")
        @Expose
        private Integer recordFrom;
        @SerializedName("record_to")
        @Expose
        private Integer recordTo;
        @SerializedName("total_record")
        @Expose
        private Integer totalRecord;

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getRecordFrom() {
            return recordFrom;
        }

        public void setRecordFrom(Integer recordFrom) {
            this.recordFrom = recordFrom;
        }

        public Integer getRecordTo() {
            return recordTo;
        }

        public void setRecordTo(Integer recordTo) {
            this.recordTo = recordTo;
        }

        public Integer getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(Integer totalRecord) {
            this.totalRecord = totalRecord;
        }

    }
    public class TagDataObject {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String tagName;
        @SerializedName("total_prospects")
        @Expose
        private String totalProspects;
//        @SerializedName("start_campaign")
//        @Expose
//        private String startCampaign;
      /*  @SerializedName("campaigns")
        @Expose
        private List<Object> campaigns = null;*/
//        @SerializedName("creteria")
//        @Expose
//        private List<Creteria> creteria = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getTotalProspects() {
            return totalProspects;
        }

        public void setTotalProspects(String totalProspects) {
            this.totalProspects = totalProspects;
        }

//        public String getStartCampaign() {
//            return startCampaign;
//        }
//
//        public void setStartCampaign(String startCampaign) {
//            this.startCampaign = startCampaign;
//        }

     /*   public List<Object> getCampaigns() {
            return campaigns;
        }

        public void setCampaigns(List<Object> campaigns) {
            this.campaigns = campaigns;
        }*/

//        public List<Creteria> getCreteria() {
//            return creteria;
//        }
//
//        public void setCreteria(List<Creteria> creteria) {
//            this.creteria = creteria;
//        }
       /* public String strCateria()
        {
            String cateria="";
            for(int i=0;i<getCreteria().size();i++)
            {
                Creteria creteria=getCreteria().get(i);
                if(i==0){
                    int length=0;
                    if(cateria.length()>0){length=creteria.getField().length();}
                    else{
                    length=creteria.getField().length()-1;
                    }

                cateria=AppConstant.makeStringBold(creteria.getField(),length)+":"+creteria.getValue();
                }
                else
                    {
                        StringBuilder builder=new StringBuilder();
                        int length=0;
                        if(cateria.length()>0){length=creteria.getField().length();}
                        else{
                            length=creteria.getField().length()-1;
                        }
                        if(null != creteria.getOperator()) {
                            cateria = builder.append(cateria)+" "+creteria.getOperator()+" "+AppConstant.makeStringBold(creteria.getField(), length) + ":" + creteria.getValue();
                        }
                        else{
                            cateria = cateria+AppConstant.makeStringBold(creteria.getField(), length) + ":" + creteria.getValue();
                        }
                    }
             }
            return cateria;
        }*/
    }


    public class Creteria {

        @SerializedName("field")
        @Expose
        private String field;
        @SerializedName("value")
        @Expose
        private String value;
        @SerializedName("operator")
        @Expose
        private String operator;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

    }

}

