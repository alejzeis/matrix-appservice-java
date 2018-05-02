/*
 * Copyright © 2018, jython234
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.jython234.matrix.appservice;

import io.github.jython234.matrix.appservice.event.EventHandler;
import io.github.jython234.matrix.appservice.exception.KeyNotFoundException;
import io.github.jython234.matrix.appservice.registration.Registration;
import io.github.jython234.matrix.appservice.registration.RegistrationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a simple Matrix Appservice. This class
 * is also auto-configured to be the entry point for the SpringBoot
 * application.
 *
 * @author jython234
 */
@SpringBootApplication
public class MatrixAppservice {
    private static MatrixAppservice INSTANCE;

    /**
     * The Logger instance that the Appservice is using.
     * It is recommended you use your own logger instead of this
     * one to keep things more tidy.
     */
    public final Logger logger;

    private Registration registration;
    private EventHandler eventHandler;


    /**
     * Only for Tests, do not USE!
     */
    MatrixAppservice() {
        MatrixAppservice.INSTANCE = this;

        this.logger = LoggerFactory.getLogger("Appservice");

        this.loadRegistration("tmp/registration.yml");
    }

    /**
     * Construct a new MatrixAppservice instance with the following
     * registration file.
     * @param registrationLocation The location of the registration YAML file the appservice
     *                             needs.
     */
    public MatrixAppservice(String registrationLocation) {
        MatrixAppservice.INSTANCE = this;

        this.logger = LoggerFactory.getLogger("Appservice");

        this.loadRegistration(registrationLocation);
    }

    /**
     * Get the current Instance of the MatrixAppservice class.
     * @return The current instance of the MatrixAppservice class.
     */
    public static MatrixAppservice getInstance() {
        return MatrixAppservice.INSTANCE;
    }

    /**
     * Start the appservice and begin listening for
     * requests.
     */
    public void run() {
        SpringApplication.run(MatrixAppservice.class);
    }

    private void loadRegistration(String registrationLocation) {
        this.logger.info("Loading registration file from: " + registrationLocation);

        var location = new File(registrationLocation);
        try {
            this.registration = RegistrationLoader.loadRegistrationFromFile(location);
        } catch(FileNotFoundException e) {
            this.logger.error("Failed to load registration file, not found!");
            this.logger.error("Please generate a registration file and place it in \"" + registrationLocation + "\"");
            throw new RuntimeException(e);
        } catch (KeyNotFoundException e) {
            this.logger.error("The registration file is invalid! Key Not Found!");
            this.logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Get this appservice's registration.
     * @return The Registration instance this appservice is using.
     */
    public Registration getRegistration() {
        return this.registration;
    }

    /**
     * Get this appservice's event handler.
     * @return The EventHandler.
     */
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * Set this appservice's event handler.
     * @param e The EventHandler.
     */
    public void setEventHandler(EventHandler e) {
        this.eventHandler = e;
    }
}
