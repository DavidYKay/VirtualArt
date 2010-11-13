/*
 * Copyright (C) 2010- Peer internet solutions
 * 
 * This file is part of mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */
package com.virtualart.virtualart.render;

/**
 * @author daniele
 *
 */
public class ARVector {
	public float x;
	public float y;
	public float z;

	public ARVector() {
		this(0, 0, 0);
	}

	public ARVector(ARVector v) {
		this(v.x, v.y, v.z);
	}

	public ARVector(float v[]) {
		this(v[0], v[1], v[2]);
	}

	public ARVector(float x, float y, float z) {
		set(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		ARVector v = (ARVector) obj;
		return (v.x == x && v.y == y && v.z == z);
	}

	public boolean equals(float x, float y, float z) {
		return (this.x == x && this.y == y && this.z == z);
	}

	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}

	public void set(ARVector v) {
		set(v.x, v.y, v.z);
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add(ARVector v) {
		add(v.x, v.y, v.z);
	}

	public void subtract(float x, float y, float z) {
		add(-x, -y, -z);
	}

	public void subtract(ARVector v) {
		add(-v.x, -v.y, -v.z);
	}

	public void multiply(float s) {
		x *= s;
		y *= s;
		z *= s;
	}

	public void divide(float s) {
		x /= s;
		y /= s;
		z /= s;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float length2D() {
		return (float) Math.sqrt(x * x + z * z);
	}

	public void normalize() {
		divide(length());
	}

	public float dot(ARVector v) {
		return x * v.x + y * v.y + z * v.z;
	}

    /**
     * Cross product
     */
	public void cross(ARVector u, ARVector v) {
		float xTemp = u.y * v.z - u.z * v.y;
		float yTemp = u.z * v.x - u.x * v.z;
		float zTemp = u.x * v.y - u.y * v.x;

		this.x = xTemp;
		this.y = yTemp;
		this.z = zTemp;
	}

    /**
     * Dot product?
     */
	public void prod(Matrix m) {
		float xTemp = m.a1 * x + m.a2 * y + m.a3 * z;
		float yTemp = m.b1 * x + m.b2 * y + m.b3 * z;
		float zTemp = m.c1 * x + m.c2 * y + m.c3 * z;

		this.x = xTemp;
		this.y = yTemp;
		this.z = zTemp;
	}
}
