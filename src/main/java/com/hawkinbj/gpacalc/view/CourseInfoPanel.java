/*     */ package com.hawkinbj.gpacalc.view;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Map;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Grade;


/*     */ 
/*     */ public class CourseInfoPanel extends GUIPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1488575000302467412L;
/*     */   private JPanel infoPanel;
/*     */   private JPanel gradeTypesPanel;
/*     */   private JPanel navigationPanel;
/*     */   private JComboBox letterGradeComboBox;
/*     */   private JLabel finalGradeLabel;
/*     */ 
/*     */   public CourseInfoPanel(SystemController controller)
/*     */   {
/*  22 */     super(controller);
/*  23 */     addComponentsToPane();
/*     */   }
/*     */ 
/*     */   private void addComponentsToPane()
/*     */   {
/*  29 */     this.infoPanel = new JPanel();
/*  30 */     this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));
/*  31 */     createTitledBorder(this.infoPanel, this.controller.getActiveCourse().getCourseName());
/*  32 */     this.infoPanel.add(new JLabel("Credit hours: " + 
/*  33 */       Integer.toString(this.controller.getActiveCourse().getCreditHours())));
/*     */ 
/*  35 */     this.finalGradeLabel = new JLabel("Final grade: " + 
/*  36 */       this.controller.getActiveCourse().getFinalGrade());
/*  37 */     this.infoPanel.add(this.finalGradeLabel);
/*     */ 
/*  39 */     JLabel currentAvg = new JLabel();
/*  40 */     Double average = Double.valueOf(0.0D);
/*     */ 
/*  42 */     if (this.controller.getActiveCourse().getWeighted()) {
/*  43 */       average = Double.valueOf(this.controller.getActiveCourse().getWeightedTotalPointsEarned());
/*  44 */       if (Double.isNaN(average.doubleValue()))
/*  45 */         currentAvg.setText("Current average: N/A");
/*     */       else
/*  47 */         currentAvg.setText("Current average: " + 
/*  48 */           String.format("%.2f", new Object[] { average }));
/*     */     } else {
/*  50 */       average = Double.valueOf(this.controller.getActiveCourse().getTotalPointsEarned() / 
/*  51 */         this.controller.getActiveCourse().getTotalPointsPossible());
/*  52 */       if (Double.isNaN(average.doubleValue()))
/*  53 */         currentAvg.setText("Current average: N/A");
/*     */       else
/*  55 */         currentAvg
/*  56 */           .setText("Current average: " + 
/*  57 */           String.format(
/*  58 */           "%.2f", new Object[] { 
/*  59 */           Double.valueOf(this.controller.getActiveCourse()
/*  60 */           .getTotalPointsEarned() / this.controller.getActiveCourse()
/*  61 */           .getTotalPointsPossible()) }));
/*     */     }
/*  63 */     this.infoPanel.add(currentAvg);
/*     */ 
/*  66 */     this.gradeTypesPanel = new JPanel();
/*  67 */     this.gradeTypesPanel.setLayout(new BoxLayout(this.gradeTypesPanel, 
/*  68 */       3));
/*  69 */     createTitledBorder(this.gradeTypesPanel, "Select Grade Type");
/*  70 */     for (String gradeType : this.controller.getActiveCourse().getGrades().keySet()) {
/*  71 */       this.gradeTypesPanel.add(createButton(gradeType));
/*     */     }
/*     */ 
/*  74 */     this.gradeTypesPanel.add(createButton("setFinalGrade", "Final Grade"));
/*     */ 
/*  77 */     this.navigationPanel = new JPanel();
/*  78 */     this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 
/*  79 */       3));
/*  80 */     createTitledBorder(this.navigationPanel, "Navigation");
/*     */ 
/*  82 */     this.navigationPanel.add(createButton("edit", "Edit Course..."));
/*     */ 
/*  84 */     this.navigationPanel.add(createButton("back", "Back"));
/*     */ 
/*  86 */     setLayout(new BoxLayout(this, 3));
/*  87 */     add(this.infoPanel);
/*  88 */     add(this.gradeTypesPanel);
/*  89 */     add(this.navigationPanel);
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent e) {
/*  93 */     String action = e.getActionCommand();
/*  94 */     if (action.equals("back")) {
/*  95 */       this.controller.setActiveCourse(null);
/*     */ 
/*  97 */       this.controller.getRootFrame().addPanel(new CoursePanel(this.controller), this);
/*  98 */     } else if (action.equals("setFinalGrade")) {
/*  99 */       this.controller.getRootFrame()
/* 100 */         .addPanel(new FinalGradePanel(this.controller), this);
/* 101 */     } else if (action.equals("edit")) {
/* 102 */       this.controller.getRootFrame().addPanel(new CourseDialog(this.controller), this);
/*     */     } else {
/* 104 */       this.controller.getRootFrame().addPanel(new GradePanel(this.controller, 
/* 105 */         (Grade)this.controller.getActiveCourse().getGrades().get(action)), this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mouseClicked(MouseEvent e)
/*     */   {
/* 111 */     Component component = e.getComponent();
/*     */ 
/* 113 */     if ((SwingUtilities.isRightMouseButton(e)) && (component.getName() != null)) {
/* 114 */       if (component.getName().equals("setFinalGrade"))
/* 115 */         return;
/* 116 */       int response = JOptionPane.showConfirmDialog(this, 
/* 117 */         "Are you sure you wish to remove this grade type?", 
/* 118 */         "Confirm", 0, 
/* 119 */         3);
/* 120 */       if (response == 0) {
/* 121 */         this.controller.getActiveCourse().removeGrade(component.getName());
/* 122 */         this.controller.saveUserList();
/* 123 */         this.gradeTypesPanel.remove(component);
/* 124 */         this.gradeTypesPanel.revalidate();
/* 125 */         this.gradeTypesPanel.repaint();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Brandon\Development\git\gpacalc\bin\
 * Qualified Name:     com.hawkinbj.gpacalc.CourseInfoPanel
 * JD-Core Version:    0.6.1
 */