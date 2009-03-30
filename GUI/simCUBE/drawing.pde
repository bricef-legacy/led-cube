



void smallFCC(LedCube c, int s){
/*
  * FCC structure method by Samuel Dove 03/2009
  * for use with LedCube and LED classes by Edd Overton 03/2009
  */
 
  c.clearCube();
 
 
  for(int z=0; z<s; z++){
   for(int y=0; y<s; y++){
    for(int x=0; x<s; x++){
  
      //odd layer
    if(z%2 != 0){
    if(y%2 == 0){
     if(x%2 == 0){c.setLED(3, x, y, z);}
    }else{
     if(x%2 != 0){c.setLED(3, x, y, z);}
    }
    }
    //even layer
    if(z%2 == 0){
      if(y%2 ==0){
       if(x%2 !=0){c.setLED(3, x, y, z);}
      }else{
        if(x%2 == 0){c.setLED(3, x, y, z);}
      }

    }}}}
  /*
  * END FCC Structure Code
  */
}

void smallBCC(LedCube c, int s){
 
 c.clearCube();
  
  for(int z = 0; z< s; z++){
   for(int y = 0; y<s; y++){
    for(int x = 0; x < s; x++){
     
      //odd layer
      if(z%2 !=0){
        if(y%2 !=0){
           if(x%2 !=0){ 
       c.setLED(3, x, y, z);
       }}}else{
        if(y%2 == 0){ 
         if(x%2 == 0){
          c.setLED(3, x, y, z); 
         }}} 
    }}}   
}

void largeBCC(LedCube c, int s){
    c.clearCube();
    
    for(int z = 0; z< s; z++){
     for(int y = 0; y<s; y++){
      for(int x = 0; x < s; x++){
      
      if( z==1 || z == 2 || z == 6 || z == 7){
        if( x==1 || x == 2 || x == 6 || x == 7){
          if(y==1 || y == 2 || y == 6 || y == 7){
            c.setLED(3, x,y, z);
          }
        }
      }
      
      if( z == 3 || z == 4 || z ==5){
         if( x == 3 || x == 4 || x ==5){
           if( y == 3 || y == 4 || y ==5){
              c.setLED(3, x, y, z);
      
      
        
      }}}
      if(z == 0){
        if( y ==0){
      c.setLED(1, x, y, z);
        }
        if( x ==0){
        c.setLED(1, x, y, z);
        }
      }
      if(x == 0 && y ==0){
       c.setLED(1, x, y, z); 
      }
    }}}

}

void largeFCC(LedCube c, int s){
   c.clearCube();
    
    for(int z = 0; z< s; z++){
     for(int y = 0; y<s; y++){
      for(int x = 0; x < s; x++){
      
          //draws axis
  
     
  if(y==0 && z ==0){
       c.setLED(1, x, y, z);}
  if(y==0 && x ==0){
       c.setLED(1, x, y, z);}  
  if(x==0 && z ==0){
       c.setLED(1, x, y, z);}
        
        //corner code
      if( z==1 || z == 2 || z == 6 || z == 7){
        if( x==1 || x == 2 || x == 6 || x == 7){
          if(y==1 || y == 2 || y == 6 || y == 7){
            c.setLED(3, x,y, z);
          }}}
      
      //face code
      int fc = 2;
      //bottom
      if ( z == 1 || z == 2){
       if (x == 3 || x==4 || x==5){
        if ( y ==3 || y==4 || y ==5){
         c.setLED(fc, x, y, z); 
        }}}
        //top
        if ( z == 6 || z == 7){
       if (x == 3 || x==4 || x==5){
        if ( y ==3 || y==4 || y ==5){
         c.setLED(fc, x, y, z); 
        }}}
      //side
      if ( x == 1 || x == 2){
       if (z == 3 || z==4 || z==5){
        if ( y ==3 || y==4 || y ==5){
         c.setLED(fc, x, y, z); 
        }}}
      
      if ( x == 6 || x == 7){
       if (z == 3 || z==4 || z==5){
        if ( y ==3 || y==4 || y ==5){
         c.setLED(fc, x, y, z); 
        }}}
        
        if ( y == 1 || y == 2){
       if (z == 3 || z==4 || z==5){
        if ( x ==3 || x==4 || x ==5){
         c.setLED(fc, x, y, z); 
        }}}
        
        if ( y == 6 || y == 7){
       if (z == 3 || z==4 || z==5){
        if ( x ==3 || x==4 || x ==5){
         c.setLED(fc, x, y, z); 
        }}}
      
      
      
      }}}
  
}


void drawMiller(LedCube c, int i, int j, int k, int s){
  
  c.clearCube();
  
  for(int z = 0; z< s; z++){
     for(int y = 0; y<s; y++){
      for(int x = 0; x < s; x++){
        
        if ( i == 1 && j == 0 && k == 0){
         if (x == 0){
          c.setLED(3, x, y, z);
         }}
        
        if ( j == 1 && i == 0 && k==0){
         if(y == 0){
          c.setLED(3, x, y, z);
         } 
        }
        
        if( i == 0 && j == 0 && k ==1){
         if(k == 0){
          c.setLED(3, x, y, z);
         } 
        }
        
        if ( i == 1 && j == 1 && k ==0){
         if ( x == y){
          c.setLED(3, x, y, z);
         } 
        }
        
        if( i == 0 && j ==1 && k ==1){
         if(y == z){
          c.setLED(3, x, y, z);
         } 
        }
        
        if ( i == 1 && j == 0 && k ==1){
         if ( x == y){ 
         c.setLED(3, x, y, z);
         }
        }
        
        if( i ==1 && j ==1 && k ==1){
         if( x == y && y != z){
          c.setLED(3, x, y, z);
         } 
        }
        
      }}}
  
  
}



void drawsc(LedCube c, int s){
 
 c.clearCube();
  
   
       
  for(int z = 0; z< s; z++){
   for(int y = 0; y<s; y++){
    for(int x = 0; x < s; x++){
      
          //draws axis
          
  if(y==0 && z ==0){
       c.setLED(1, x, y, z);}
  if(y==0 && x ==0){
       c.setLED(1, x, y, z);}  
  if(x==0 && z ==0){
       c.setLED(1, x, y, z);}

//draws lattice
      if(z%2 != 0){
        if(x%2 != 0){
          if(y%2 !=0){
       c.setLED(3, x, y, z);
                      }
                    }
                  }
       }}}
       
}

void largeSC(LedCube c, int s){
 
 c.clearCube();
  
   
       
  for(int z = 0; z< s; z++){
   for(int y = 0; y<s; y++){
    for(int x = 0; x < s; x++){
      
          //draws axis
  
     
  if(y==0 && z ==0){
       c.setLED(1, x, y, z);}
  if(y==0 && x ==0){
       c.setLED(1, x, y, z);}  
  if(x==0 && z ==0){
       c.setLED(1, x, y, z);}


//draws lattice
     
     if( z==1 || z == 2 || z == 6 || z == 7){
        if( x==1 || x == 2 || x == 6 || x == 7){
          if(y==1 || y == 2 || y == 6 || y == 7){
            c.setLED(2, x,y, z);
          }}}
     
       }}}
       
}

/*
  * Miller method by Chris Ryan 03/2009
  * for use with LedCube and LED classes by Edd Overton 03/2009
  */
void miller (LedCube c, int s, int h, int k, int l){
  
  c.clearCube();
  
  for(int z = 0; z< s; z++){
        for(int y = 0; y<s; y++){
         for(int x = 0; x < s; x++){
           for(int d = -10; d<10; d++){
             
             //axis
             if(y==0 && z ==0){
       c.setLED(1, x, y, z);}
  if(y==0 && x ==0){
       c.setLED(1, x, y, z);}  
  if(x==0 && z ==0){
       c.setLED(1, x, y, z);}
       
       //end axis
       
       //draw plane..
         if(l!=0){  
    if(z==((-1*x*h/l)-(y*k/l)+(d*7/l)))
       {
         c.setLED(3,x,y,z);
       }
         }
       //end of draw plane
       if(l==0 && h!=0){
    if(x==((-1*y*k/h)+(d*7)))
       {
         c.setLED(3,x,y,z);
       }
       }
       if(l==0 && h==0){
    if(y==(d*7/k))
       {
         c.setLED(3,x,y,z);
       }
       }
       

         }}}}
}
      
