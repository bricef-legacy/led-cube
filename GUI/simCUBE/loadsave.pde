/**
   * Load / Save functions for saving the cube to a 2D bitmap Image
   * Important: DONT USE TIF, processing doesent like opened files.
   * Edward Overton
   */

void saveCube(LedCube cube, String filename)
{
  int a,b,c;
  PGraphics myimage = createGraphics(64, 8, P2D);
  
  int cubestates [][][] = new int[8][8][8];
  cubestates = cube.readCube();
  
  for (a=0; a<8; a++) for (b=0; b<8; b++) for (c=0; c<8; c++)
  {
    if (cubestates[a][b][c] == 0) myimage.pixels[64*a + 8*b + c] = color(0, 0, 0);
    if (cubestates[a][b][c] == 1) myimage.pixels[64*a + 8*b + c] = color(255, 0, 0);
    if (cubestates[a][b][c] == 2) myimage.pixels[64*a + 8*b + c] = color(0, 255, 0);
    if (cubestates[a][b][c] == 3) myimage.pixels[64*a + 8*b + c] = color(255, 255, 0);
  }
  myimage.save(filename);
}

void loadCube(LedCube cube, String filename)
{
  int a,b,c;
  PImage myimage = loadImage(filename);
  
  int cubestates [][][] = new int[8][8][8];
  
  for (a=0; a<8; a++) for (b=0; b<8; b++) for (c=0; c<8; c++)
  {
    if (myimage.pixels[64*a + 8*b + c] == color(0, 0, 0)) cubestates[a][b][c] = 0;
    if (myimage.pixels[64*a + 8*b + c] == color(255, 0, 0)) cubestates[a][b][c] = 1;
    if (myimage.pixels[64*a + 8*b + c] == color(0, 255, 0)) cubestates[a][b][c] = 2;
    if (myimage.pixels[64*a + 8*b + c] == color(255, 255, 0)) cubestates[a][b][c] = 3;
  }
  
  cube.setCube(cubestates);
  
}

