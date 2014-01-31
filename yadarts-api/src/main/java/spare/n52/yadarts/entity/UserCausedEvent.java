/**
 * Copyright 2014 the staff of 52°North Initiative for Geospatial Open
 * Source Software GmbH in their free time
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spare.n52.yadarts.entity;

/**
 * Interface for an event caused by the user (e.g. button pressed)
 */
public interface UserCausedEvent extends InteractionEvent {

	/**
	 * The types of a {@link UserCausedEvent}
	 */
	public static enum Type {
		DART_MISSED,
		BOUNCE_OUT,
		NEXT_PLAYER
	}
	
	/**
	 * @return the type of the event
	 */
	public Type getType();
	
}
