/* San Angeles Observation OpenGL ES version example
 * Copyright 2004-2005 Jetro Lauha
 * All rights reserved.
 * Web: http://iki.fi/jetro/
 *
 * This source is free software; you can redistribute it and/or
 * modify it under the terms of EITHER:
 *   (1) The GNU Lesser General Public License as published by the Free
 *       Software Foundation; either version 2.1 of the License, or (at
 *       your option) any later version. The text of the GNU Lesser
 *       General Public License is included with this source in the
 *       file LICENSE-LGPL.txt.
 *   (2) The BSD-style license that is included with this source in
 *       the file LICENSE-BSD.txt.
 *
 * This source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the files
 * LICENSE-LGPL.txt and LICENSE-BSD.txt for more details.
 *
 * $Id: demo.c,v 1.10 2005/02/08 20:54:39 tonic Exp $
 * $Revision: 1.10 $
 */

#include <stdlib.h>
#include <math.h>
#include <float.h>
#include <assert.h>

#include "importgl.h"
#include "app.h"

/******************************************************************************/
/* Called Direcly from app framework.                                         */
/******************************************************************************/
/* Called from the app framework. */
void appInit()
{
    glMatrixMode(GL_MODELVIEW);             
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    // glClearDepth(1.0);
    glDepthFunc(GL_LESS);                   // TODO: What is this for
    glEnable(GL_DEPTH_TEST);
    glShadeModel(GL_SMOOTH);

    glMatrixMode(GL_SMOOTH);
    glLoadIdentity();

    // TODO: need to correct the dimensions of the viewport
    glOrthof(-1.0,
              1.0,
             -1.0 / (320.0/480.0),
              1.0 / (320.0/480.0),
              0.01,
              10000.0);
    glViewport(0, 0, 320, 480);

//`    glOrthof(-1.0,
//`              1.0,
//`             -1.0 / (320.0/480.0),
//`              1.0 / (320.0/480.0),
//`              0.01,
//`              10000.0);
//`    glViewport(0, 0, 320, 480);
}
/*
static void prepareFrame(int width, int height)
{
    glViewport(0, 0, width, height);

    glClearColorx((GLfixed)(0.1f * 65536),
                  (GLfixed)(0.2f * 65536),
                  (GLfixed)(0.3f * 65536), 0x10000);
    glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // gluPerspective(45, (float)width / height, 0.5f, 150);
    glOrthof(-1.0,
              1.0,
             -1.0 / (width/height),
              1.0 / (width/height),
              0.01,
              10000.0);

    glMatrixMode(GL_MODELVIEW);

    glLoadIdentity();
}*/

// Called from the app framework.
/* The tick is current time in milliseconds, width and height
 * are the image dimensions to be rendered.
 */
void appRender(int width, int height)
{
    // Prepare OpenGL ES for rendering of the frame.
    //prepareFrame(width, height);
    // Do nothing
}

// Called from the app framework.
void appDeinit()
{
    // Called when finishing up.
}


/******************************************************************************/
/*                                                                            */
/* Other Functions to be investigated.                                        */
/*                                                                            */
/******************************************************************************/

// TODO: look into this
// static void gluPerspective(GLfloat fovy, GLfloat aspect,
//                            GLfloat zNear, GLfloat zFar)
// {
//     GLfloat xmin, xmax, ymin, ymax;
// 
//     ymax = zNear * (GLfloat)tan(fovy * PI / 360);
//     ymin = -ymax;
//     xmin = ymin * aspect;
//     xmax = ymax * aspect;
// 
//     glFrustumx((GLfixed)(xmin * 65536), (GLfixed)(xmax * 65536),
//                (GLfixed)(ymin * 65536), (GLfixed)(ymax * 65536),
//                (GLfixed)(zNear * 65536), (GLfixed)(zFar * 65536));
// }
// 
// // TODO: look at this
// 
// /* Following gluLookAt implementation is adapted from the
//  * Mesa 3D Graphics library. http://www.mesa3d.org
//  * NOTE: Not used for now.
//  */
// static void gluLookAt(GLfloat eyex, GLfloat eyey, GLfloat eyez,
// 	              GLfloat centerx, GLfloat centery, GLfloat centerz,
// 	              GLfloat upx, GLfloat upy, GLfloat upz)
// {
//     GLfloat m[16];
//     GLfloat x[3], y[3], z[3];
//     GLfloat mag;
// 
//     /* Make rotation matrix */
// 
//     /* Z vector */
//     z[0] = eyex - centerx;
//     z[1] = eyey - centery;
//     z[2] = eyez - centerz;
//     mag = (float)sqrt(z[0] * z[0] + z[1] * z[1] + z[2] * z[2]);
//     if (mag) {			/* mpichler, 19950515 */
//         z[0] /= mag;
//         z[1] /= mag;
//         z[2] /= mag;
//     }
// 
//     /* Y vector */
//     y[0] = upx;
//     y[1] = upy;
//     y[2] = upz;
// 
//     /* X vector = Y cross Z */
//     x[0] = y[1] * z[2] - y[2] * z[1];
//     x[1] = -y[0] * z[2] + y[2] * z[0];
//     x[2] = y[0] * z[1] - y[1] * z[0];
// 
//     /* Recompute Y = Z cross X */
//     y[0] = z[1] * x[2] - z[2] * x[1];
//     y[1] = -z[0] * x[2] + z[2] * x[0];
//     y[2] = z[0] * x[1] - z[1] * x[0];
// 
//     /* mpichler, 19950515 */
//     /* cross product gives area of parallelogram, which is < 1.0 for
//      * non-perpendicular unit-length vectors; so normalize x, y here
//      */
// 
//     mag = (float)sqrt(x[0] * x[0] + x[1] * x[1] + x[2] * x[2]);
//     if (mag) {
//         x[0] /= mag;
//         x[1] /= mag;
//         x[2] /= mag;
//     }
// 
//     mag = (float)sqrt(y[0] * y[0] + y[1] * y[1] + y[2] * y[2]);
//     if (mag) {
//         y[0] /= mag;
//         y[1] /= mag;
//         y[2] /= mag;
//     }
// 
// #define M(row,col)  m[col*4+row]
//     M(0, 0) = x[0];
//     M(0, 1) = x[1];
//     M(0, 2) = x[2];
//     M(0, 3) = 0.0;
//     M(1, 0) = y[0];
//     M(1, 1) = y[1];
//     M(1, 2) = y[2];
//     M(1, 3) = 0.0;
//     M(2, 0) = z[0];
//     M(2, 1) = z[1];
//     M(2, 2) = z[2];
//     M(2, 3) = 0.0;
//     M(3, 0) = 0.0;
//     M(3, 1) = 0.0;
//     M(3, 2) = 0.0;
//     M(3, 3) = 1.0;
// #undef M
//     {
//         int a;
//         GLfixed fixedM[16];
//         for (a = 0; a < 16; ++a)
//             fixedM[a] = (GLfixed)(m[a] * 65536);
//         glMultMatrixx(fixedM);
//     }
// 
//     /* Translate Eye to Origin */
//     glTranslatex((GLfixed)(-eyex * 65536),
//                  (GLfixed)(-eyey * 65536),
//                  (GLfixed)(-eyez * 65536));
// }
