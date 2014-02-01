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
package spare.n52.yadarts.entity.impl;

import spare.n52.yadarts.entity.PointEvent;

public class HitEvent implements PointEvent {

	private long time;
	private int base;
	private int multi;

	private HitEvent(int base) {
		this(base, 1);
	}
	
	private HitEvent(int base, int multi) {
		this.time = System.currentTimeMillis();
		this.base = base;
		this.multi = multi;
	}
	
	@Override
	public long getTimestamp() {
		return this.time;
	}

	@Override
	public int getBaseNumber() {
		return this.base;
	}

	@Override
	public int getMutliplier() {
		return this.multi;
	}
	
	@Override
	public String toString() {
		switch (this.multi) {
		case 1:
			return String.format("%d (%s)", this.base, this.time);
		case 2:
			return String.format("Double %d (%s)", this.base, this.time);
		case 3:
			return String.format("Triple %d (%s)", this.base, this.time);
		default:
			return String.format("%d (%s)", this.base, this.time);
		}
	}
	
	public static PointEvent singleHit(int number) {
		return new HitEvent(number);
	}
	
	public static PointEvent doubleHit(int number) {
		return new HitEvent(number, 2);
	}
	
	public static PointEvent tripleHit(int number) {
		return new HitEvent(number, 3);
	}
	
}