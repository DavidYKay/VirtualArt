package com.virtualart.virtualart.render;

import java.nio.FloatBuffer;

public class Matrix4f {

  private float a1, a2, a3, a4;
  private float b1, b2, b3, b4;
  private float c1, c2, c3, c4;
  private float d1, d2, d3, d4;

  //private FloatBuffer mBuffer = FloatBuffer.allocate(16);

  //public static class MatrixFactory {
  //  public static Matrix createMatrixFromArray(float[] rotationMatrix) {
  //    if (rotationMatrix.length == 16) {
  //      return new Matrix(
  //          rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
  //          rotationMatrix[4], rotationMatrix[5], rotationMatrix[6],
  //          rotationMatrix[8], rotationMatrix[9], rotationMatrix[10]
  //          );
  //    } else if (rotationMatrix.length == 9) {

  //      return new Matrix(
  //          rotationMatrix[0], rotationMatrix[1], rotationMatrix[2],
  //          rotationMatrix[3], rotationMatrix[4], rotationMatrix[5],
  //          rotationMatrix[6], rotationMatrix[7], rotationMatrix[8]
  //          );
  //    } else {
  //      throw new IllegalArgumentException("Array length must be 16 or 9.");
  //    }
  //  }
  //}

  public Matrix4f() {
    //Empty
  }

  public Matrix4f(float[] array) {
    this(
        array[0], array[1], array[2], array[3],
        array[4], array[5], array[6], array[7],
        array[8], array[9], array[10], array[11],
        array[12], array[13], array[14], array[15]
    );
  }

  public Matrix4f( float a1, float a2, float a3, float a4,
                 float b1, float b2, float b3, float b4,
                 float c1, float c2, float c3, float c4,
                 float d1, float d2, float d3, float d4
               ) {
    this.set(
        a1,  a2,  a3,  a4,
        b1,  b2,  b3,  b4,
        c1,  c2,  c3,  c4,
        d1,  d2,  d3,  d4
        );
  }

  public void set(
      float a1, float a2, float a3, float a4,
      float b1, float b2, float b3, float b4,
      float c1, float c2, float c3, float c4,
      float d1, float d2, float d3, float d4
  ) {
    this.a1 = a1;
    this.a2 = a2;
    this.a3 = a3;
    this.a4 = a4;

    this.b1 = b1;
    this.b2 = b2;
    this.b3 = b3;
    this.b4 = b4;

    this.c1 = c1;
    this.c2 = c2;
    this.c3 = c3;
    this.c4 = c4;

    this.d1 = d1;
    this.d2 = d2;
    this.d3 = d3;
    this.d4 = d4;
  }


  //public void set(Matrix m) {
  //  this.a1 = m.a1;
  //  this.a2 = m.a2;
  //  this.a3 = m.a3;

  //  this.b1 = m.b1;
  //  this.b2 = m.b2;
  //  this.b3 = m.b3;

  //  this.c1 = m.c1;
  //  this.c2 = m.c2;
  //  this.c3 = m.c3;
  //}

  //public void invert() {
  //  float det = this.det();

  //  adj();
  //  multiply(1 / det);
  //}

  public Matrix4f transpose() {
    return new Matrix4f(
        a1, b1, c1, d1,
        a2, b2, c2, d2,
        a3, b3, c3, d3,
        a4, b4, c4, d4
    );
  }
  public float[] getArray() {
    return new float[] {
        a1, b1, c1, d1,
        a2, b2, c2, d2,
        a3, b3, c3, d3,
        a4, b4, c4, d4
    };
  }

  //@Override
  //public String toString() {

  //  String[] array = new String[] {
  //    String.format("%.1f", a1),
  //        String.format("%.1f", a2),
  //        String.format("%.1f", a3),
  //        String.format("%.1f", b1),
  //        String.format("%.1f", b2),
  //        String.format("%.1f", b3),
  //        String.format("%.1f", c1),
  //        String.format("%.1f", c2),
  //        String.format("%.1f", c3)
  //  };

  //  StringBuilder sb = new StringBuilder();

  //  for (int i = 0; i < array.length; i++) {
  //    sb.append(array[i]);
  //    if ((i + 1) % 3 == 0) {
  //      sb.append("\n");
  //    } else {
  //      sb.append(", ");
  //    }
  //  }
  //  return sb.toString();
  //  //return "[ (" + a1 + "," + a2 + "," + a3 + ") (" + b1 + "," + b2 + "," + b3 + ") (" + c1 + "," + c2 + "," + c3 + ") ]";
  //}

  //public static String matrixToString(float[] matrix) {
  //  return new Matrix(
  //      matrix[0], matrix[1], matrix[2],
  //      matrix[3], matrix[5], matrix[5],
  //      matrix[6], matrix[7], matrix[8]
  //      ).toString();
  //}
}
