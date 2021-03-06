/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.jbpm.process.core.timer;

import java.util.Date;
/**
 * BusinessCalendar allows for defining custom definitions of working days, hours and holidays
 * to be taken under consideration when scheduling time based activities such as timers or deadlines.
 */
public interface BusinessCalendar {

    /**
     * Calculates given time expression into duration in milliseconds based on calendar configuration.
     * 
     * @param timeExpression time expression that is supported by business calendar implementation.
     * @return duration expressed in milliseconds
     */
    public long calculateBusinessTimeAsDuration(String timeExpression);
    
    /**
     * Calculates given time expression into target date based on calendar configuration.
     * @param timeExpression time expression that is supported by business calendar implementation.
     * @return date when given time expression will match in the future
     */
    public Date calculateBusinessTimeAsDate(String timeExpression);
}
