<%--
 * Copyright 2005 Darren L. Spurgeon
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
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://ajaxtags.sourceforge.net/tags/ajaxtags"
	prefix="ajax"%>

<h1>Callout Tag Demo</h1>

<div style="font-size: 90%; width: 650px;">
<p>The <code>ajax:callout</code> tag is an easy way to attach a
callout or popup balloon to any HTML element supporting an onclick
event. The style of this callout is fairly flexible, but generally has a
header/title, a close link ('X'), and the content itself, of course.</p>

<h3>Callout in Action</h3>
<div
	style="font-size: 90%; width: 650px; border: 1px dashed #999; padding: 10px">
<p>The Hitchhiker's Guide to the Galaxy is a science fiction series
written by Douglas Adams (1952 - 2001). The series follows the
adventures of <a href="javascript://nop/" class="definition">Arthur
Dent</a>, a hapless Englishman who escapes the destruction of Earth by an
alien race called the <a href="javascript://nop/" class="definition">Vogons</a>
with his friend <a href="javascript://nop/" class="definition">Ford
Prefect</a>, an alien from a small planet somewhere in the vicinity of
Betelgeuse and researcher for the eponymous guide.</p>
</div>

<ajax:callout baseUrl="callout.view" sourceClass="definition"
	parameters="q={ajaxParameter}" title="Definition" /></div>