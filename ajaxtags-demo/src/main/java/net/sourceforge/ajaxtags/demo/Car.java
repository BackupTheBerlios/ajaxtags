/*
 * Copyright 2009-2010 AjaxTags-Team
 *
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

import net.sourceforge.ajaxtags.xml.AjaxXmlBuilder.PropertyReader;

/**
 * A Car entity with make and model properties.
 * 
 * @author Darren Spurgeon
 * @author Jens Kapitza
 */
public class Car implements Serializable, PropertyReader {

    private static final long serialVersionUID = 1274650793273603808L;
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

    /**
     * @return the name of the car e.g. the model
     */
    public String getName() {
        return getModel();
    }

    /**
     * @return the value of the car e.g. the make
     */
    public String getValue() {
        return getMake();
    }

    /**
     * @return true, cause we should send it as CData to make sure everything works fine if slashes
     *         are included
     */
    public boolean isCData() {
        return true;
    }
}
