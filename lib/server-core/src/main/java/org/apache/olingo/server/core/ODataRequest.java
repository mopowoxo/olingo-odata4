/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.server.core;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.commons.api.http.HttpMethod;

public class ODataRequest {
  private HttpMethod method;
  private Map<String, List<String>> headers = new HashMap<String, List<String>>();
  private InputStream body;
  private String rawQueryPath;
  private String rawRequestUri;
  private String rawODataPath;
  private String rawBaseUri;

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(final HttpMethod method) {
    this.method = method;
  }

  public Map<String, List<String>> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  public void setHeaders(final Map<String, List<String>> headers) {
    this.headers = headers;
  }

  public InputStream getBody() {
    return body;
  }

  public void setBody(final InputStream body) {
    this.body = body;
  }

  public String getRawQueryPath() {
    return rawQueryPath;
  }

  public void setRawQueryPath(String rawQueryPath) {
    this.rawQueryPath = rawQueryPath;
  }

  public String getRawBaseUri() {
    return rawBaseUri;
  }

  public String getRawRequestUri() {
    return rawRequestUri;
  }

  public String getRawODataPath() {
    return rawODataPath;
  }

  public void setRawRequestUri(String rawRequestUri) {
    this.rawRequestUri = rawRequestUri;
  }

  public void setRawODataPath(String rawODataPath) {
    this.rawODataPath = rawODataPath;
    
  }

  public void setRawBaseUri(String rawBaseUri) {
    this.rawBaseUri = rawBaseUri;
  }
}
