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
    
    stroke(0, 255, 0);
    beginShape(); //Y-Axis
      vertex(0, 0, 0);
      vertex(0, 0, axissize);
      vertex(0, arrowszie, axissize-arrowszie);
      vertex(0, 0, axissize);
      vertex(0, -arrowszie, axissize-arrowszie);
    endShape();
    
    stroke(0, 0, 255);
    beginShape(); //Z-Axis
      vertex(0, 0, 0);
      vertex(0, -axissize, 0);
      vertex(arrowszie, -axissize+arrowszie, 0);
      vertex(0, -axissize, 0);
      vertex(-arrowszie, -axissize+arrowszie, 0);
    endShape();
}
