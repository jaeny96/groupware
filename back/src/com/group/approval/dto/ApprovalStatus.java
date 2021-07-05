package com.group.approval.dto;

public class ApprovalStatus {

   private String apStatus_type;

   public String getApStatus_type() {
      return apStatus_type;
   }

   public void setApStatus_type(String ap_type) {
      this.apStatus_type = ap_type;
   }

   @Override
   public String toString() {
      return "ApprovalStatus [ap_type=" + apStatus_type + "]";
   }
   
}