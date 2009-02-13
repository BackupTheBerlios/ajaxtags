/**
 * Copyright 2009 Jens Kapitza
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.sourceforge.ajaxtags.demo;

import java.io.Serializable;



/**
 * A Car entity with make and model properties.
 * 
 * @author Darren Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 28 $ $Date: 2008-09-10 16:23:13 +0200 (Mi, 10. Sep 2008)
 *          $
 */
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    private String make;
    private String model;


    /**
     * Constructor for Car.
     */
    public Car() {
        this(null, null);
    }


    /**
     * Constructor for Car.
     * 
     * @param make
     * @param model
     */
    public Car(String make, String model) {
        super();
        this.make = make;
        this.model = model;
    }


    /**
     * @return Returns the make.
     */
    public String getMake() {
        return this.make;
    }


    /**
     * @param make
     *            The make to set.
     */
    public void setMake(String make) {
        this.make = make;
    }


    /**
     * @return Returns the model.
     */
    public String getModel() {
        return this.model;
    }


    /**
     * @param model
     *            The model to set.
     */
    public void setModel(String model) {
        this.model = model;
    }
}
