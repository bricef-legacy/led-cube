
void drawAxis(float axissize, float arrowszie){
  
  translate(-axissize/2, axissize/2, -axissize/2);
  stroke(255, 0, 0);
  
    beginShape(); //X-Axis
      vertex(0, 0, 0);
      vertex(axissize, 0, 0);
      vertex(axissize-arrowszie, arrowszie, 0);
      vertex(axissize, 0, 0);
      vertex(axissize-arrowszie, -arrowszie, 0);
    endShape();
    
    fill(255,0,0);
    pushMatrix();
    translate(axissize/4,0,0);
    text("X", 15, 30);
    popMatrix();
    noFill();
    
    stroke(0, 255, 0);
    beginShape(); //Y-Axis
      vertex(0, 0, 0);
      vertex(0, 0, axissize);
      vertex(0, arrowszie, axissize-arrowszie);
      vertex(0, 0, axissize);
      vertex(0, -arrowszie, axissize-arrowszie);
    endShape();
    
    fill(0,255,0);
    pushMatrix();
    translate(0,0,axissize);
    rotateY(PI/2);
    text("Y", 15, 30);
    popMatrix();
    noFill();
    
    stroke(0, 0, 255);
    beginShape(); //Z-Axis
      vertex(0, 0, 0);
      vertex(0, -axissize, 0);
      vertex(arrowszie, -axissize+arrowszie, 0);
      vertex(0, -axissize, 0);
      vertex(-arrowszie, -axissize+arrowszie, 0);
    endShape();
    
    fill(0,0,255);
    pushMatrix();
    translate(0,-axissize,0);
    //rotateY(PI/2);
    text("Z", 15, 30);
    popMatrix();
    noFill();
}
