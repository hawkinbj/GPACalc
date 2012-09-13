/*    */ package com.hawkinbj.gpacalc.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class School
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8843646586868126972L;
/*    */   private String name;
/*    */   private GradingScale gradingScale;
/*    */ 
/*    */   public School(String name, GradingScale gradingScale)
/*    */   {
/* 12 */     this.name = name;
/* 13 */     this.gradingScale = gradingScale;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public GradingScale getGradingScale() {
/* 25 */     return this.gradingScale;
/*    */   }
/*    */ 
/*    */   public void setGradingScale(GradingScale gradingScale) {
/* 29 */     this.gradingScale = gradingScale;
/*    */   }
/*    */ }

/* Location:           C:\Users\Brandon\Development\git\gpacalc\bin\
 * Qualified Name:     com.hawkinbj.gpacalc.School
 * JD-Core Version:    0.6.1
 */