/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.collect.testing.testers;

import com.google.common.collect.testing.AbstractMapTester;
import com.google.common.collect.testing.WrongType;
import com.google.common.collect.testing.features.CollectionSize;
import static com.google.common.collect.testing.features.CollectionSize.ZERO;
import com.google.common.collect.testing.features.MapFeature;
import static com.google.common.collect.testing.features.MapFeature.ALLOWS_NULL_KEYS;

/**
 * A generic JUnit test which tests {@code containsKey()} operations on a map.
 * Can't be invoked directly; please see
 * {@link com.google.common.collect.testing.MapTestSuiteBuilder}.
 *
 * @author George van den Driessche
 */
public class MapContainsKeyTester<K, V> extends AbstractMapTester<K, V> {
  @CollectionSize.Require(absent = ZERO)
  public void testContains_yes() {
    assertTrue("containsKey(present) should return true",
        getMap().containsKey(samples.e0.getKey()));
  }

  public void testContains_no() {
    assertFalse("containsKey(notPresent) should return false",
        getMap().containsKey(samples.e3.getKey()));
  }

  @MapFeature.Require(ALLOWS_NULL_KEYS)
  public void testContains_nullNotContainedButSupported() {
    assertFalse("containsKey(null) should return false",
        getMap().containsKey(null));
  }

  @MapFeature.Require(absent = ALLOWS_NULL_KEYS)
  public void testContains_nullNotContainedAndUnsupported() {
    expectNullKeyMissingWhenNullKeysUnsupported(
        "containsKey(null) should return false or throw");
  }

  @MapFeature.Require(ALLOWS_NULL_KEYS)
  @CollectionSize.Require(absent = ZERO)
  public void testContains_nonNullWhenNullContained() {
    initMapWithNullKey();
    assertFalse("containsKey(notPresent) should return false",
        getMap().containsKey(samples.e3.getKey()));
  }

  @MapFeature.Require(ALLOWS_NULL_KEYS)
  @CollectionSize.Require(absent = ZERO)
  public void testContains_nullContained() {
    initMapWithNullKey();
    assertTrue("containsKey(null) should return true",
        getMap().containsKey(null));
  }

  public void testContains_wrongType() {
    try {
      //noinspection SuspiciousMethodCalls
      assertFalse("containsKey(wrongType) should return false or throw",
          getMap().containsKey(WrongType.VALUE));
    } catch (ClassCastException tolerated) {
    }
  }
}
