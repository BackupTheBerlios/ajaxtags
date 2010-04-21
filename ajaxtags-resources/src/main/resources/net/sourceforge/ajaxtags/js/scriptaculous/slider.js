/*global $, $$, $A, $F, $H, $R, $w, Ajax, Class, Control, Effect, Element, Enumerable, Event, Field, Form, Prototype */
// script.aculo.us slider.js v1.8.3, Thu Oct 08 11:23:33 +0200 2009

// Copyright (c) 2005-2007 Marty Haught, Thomas Fuchs 
//
// script.aculo.us is freely distributable under the terms of an MIT-style license.
// For details, see the script.aculo.us web site: http://script.aculo.us/

if (!Control) {
  var Control = {};
}

// options:
//  axis: 'vertical', or 'horizontal' (default)
//
// callbacks:
//  onChange(value)
//  onSlide(value)
Control.Slider = Class.create({
  initialize: function(handle, track, options) {
    var slider = this;

    if (Object.isArray(handle)) {
      this.handles = handle.collect(function(e) {
        return $(e);
      });
    } else {
      this.handles = [$(handle)];
    }

    this.track = $(track);
    this.options = options || {};

    this.axis = this.options.axis || 'horizontal';
    this.increment = this.options.increment || 1;
    this.step = parseInt(this.options.step || '1');
    this.range = this.options.range || $R(0, 1);

    this.value = 0; // assure backwards compat
    this.values = this.handles.map(function() {
      return 0;
    });
    this.spans = this.options.spans ? this.options.spans.map(function(s) {
      return $(s);
    }) : false;
    this.options.startSpan = $(this.options.startSpan || null);
    this.options.endSpan = $(this.options.endSpan || null);

    this.restricted = this.options.restricted || false;

    this.maximum = this.options.maximum || this.range.end;
    this.minimum = this.options.minimum || this.range.start;

    // Will be used to align the handle onto the track, if necessary
    this.alignX = parseInt(this.options.alignX || '0');
    this.alignY = parseInt(this.options.alignY || '0');

    this.trackLength = this.maximumOffset() - this.minimumOffset();

    var h = this.handles[0];
    this.handleLength = this.isVertical() ? (h.offsetHeight != 0 ? h.offsetHeight : h.style.height.replace(/px$/, "")) : (h.offsetWidth != 0 ? h.offsetWidth : h.style.width.replace(/px$/, ""));

    this.active = this.dragging = this.disabled = false;

    if (this.options.disabled) {
      this.setDisabled();
    }

    // Allowed values array
    this.allowedValues = this.options.values ? this.options.values.sortBy(Prototype.K) : false;
    if (this.allowedValues) {
      this.minimum = this.allowedValues.min();
      this.maximum = this.allowedValues.max();
    }

    this.eventMouseDown = this.startDrag.bindAsEventListener(this);
    this.eventMouseUp = this.endDrag.bindAsEventListener(this);
    this.eventMouseMove = this.update.bindAsEventListener(this);

    // Initialize handles in reverse (make sure first handle is active)
    this.handles.each(function(h, i) {
      i = slider.handles.length - 1 - i;
      slider.setValue(parseFloat((Object.isArray(slider.options.sliderValue) ? slider.options.sliderValue[i] : slider.options.sliderValue) || slider.range.start), i);
      h.makePositioned().observe("mousedown", slider.eventMouseDown);
    });

    this.track.observe("mousedown", this.eventMouseDown);
    document.observe("mouseup", this.eventMouseUp);
    document.observe("mousemove", this.eventMouseMove);

    this.initialized = true;
  },
  dispose: function() {
    var slider = this;
    Event.stopObserving(this.track, "mousedown", this.eventMouseDown);
    Event.stopObserving(document, "mouseup", this.eventMouseUp);
    Event.stopObserving(document, "mousemove", this.eventMouseMove);
    this.handles.invoke("stopObserving", "mousedown", slider.eventMouseDown);
  },
  setDisabled: function() {
    this.disabled = true;
  },
  setEnabled: function() {
    this.disabled = false;
  },
  getNearestValue: function(value) {
    if (this.allowedValues) {
      if (value >= this.allowedValues.max()) {
        return (this.allowedValues.max());
      }
      if (value <= this.allowedValues.min()) {
        return (this.allowedValues.min());
      }

      var offset = Math.abs(this.allowedValues[0] - value);
      var newValue = this.allowedValues[0];
      this.allowedValues.each(function(v) {
        var currentOffset = Math.abs(v - value);
        if (currentOffset <= offset) {
          newValue = v;
          offset = currentOffset;
        }
      });
      return newValue;
    }
    return (value > this.range.end) ? this.range.end : ((value < this.range.start) ? this.range.start : value);
  },
  setValue: function(sliderValue, handleIdx) {
    if (!this.active) {
      this.activeHandleIdx = handleIdx || 0;
      this.activeHandle = this.handles[this.activeHandleIdx];
      this.updateStyles();
    }
    handleIdx = handleIdx || this.activeHandleIdx || 0;
    if (this.initialized && this.restricted) {
      if ((handleIdx > 0) && (sliderValue < this.values[handleIdx - 1])) {
        sliderValue = this.values[handleIdx - 1];
      }
      if ((handleIdx < (this.handles.length - 1)) && (sliderValue > this.values[handleIdx + 1])) {
        sliderValue = this.values[handleIdx + 1];
      }
    }
    sliderValue = this.getNearestValue(sliderValue);
    this.values[handleIdx] = sliderValue;
    this.value = this.values[0]; // assure backwards compat
    this.handles[handleIdx].style[this.isVertical() ? 'top' : 'left'] = this.translateToPx(sliderValue);

    this.drawSpans();
    if (!this.dragging || !this.event) {
      this.updateFinished();
    }
  },
  setValueBy: function(delta, handleIdx) {
    this.setValue(this.values[handleIdx || this.activeHandleIdx || 0] + delta, handleIdx || this.activeHandleIdx || 0);
  },
  translateToPx: function(value) {
    return Math.round(((this.trackLength - this.handleLength) / (this.range.end - this.range.start)) * (value - this.range.start)) + "px";
  },
  translateToValue: function(offset) {
    return ((offset / (this.trackLength - this.handleLength) * (this.range.end - this.range.start)) + this.range.start);
  },
  getRange: function(range) {
    var v = this.values.sortBy(Prototype.K);
    range = range || 0;
    return $R(v[range], v[range + 1]);
  },
  minimumOffset: function() {
    return (this.isVertical() ? this.alignY : this.alignX);
  },
  maximumOffset: function() {
    var t = this.track;
    return (this.isVertical() ? (t.offsetHeight != 0 ? t.offsetHeight : t.style.height.replace(/px$/, "")) - this.alignY : (t.offsetWidth != 0 ? t.offsetWidth : t.style.width.replace(/px$/, "")) - this.alignX);
  },
  isVertical: function() {
    return (this.axis == 'vertical');
  },
  drawSpans: function() {
    var slider = this;
    if (this.spans) {
      $R(0, this.spans.length - 1).each(function(r) {
        slider.setSpan(slider.spans[r], slider.getRange(r));
      });
    }
    if (this.options.startSpan) {
      this.setSpan(this.options.startSpan, $R(0, this.values.length > 1 ? this.getRange(0).min() : this.value));
    }
    if (this.options.endSpan) {
      this.setSpan(this.options.endSpan, $R(this.values.length > 1 ? this.getRange(this.spans.length - 1).max() : this.value, this.maximum));
    }
  },
  setSpan: function(span, range) {
    if (this.isVertical()) {
      span.style.top = this.translateToPx(range.start);
      span.style.height = this.translateToPx(range.end - range.start + this.range.start);
    } else {
      span.style.left = this.translateToPx(range.start);
      span.style.width = this.translateToPx(range.end - range.start + this.range.start);
    }
  },
  updateStyles: function() {
    this.handles.invoke('removeClassName', 'selected');
    Element.addClassName(this.activeHandle, 'selected');
  },
  startDrag: function(event) {
    if (Event.isLeftClick(event)) {
      if (!this.disabled) {
        this.active = true;

        var handle = Event.element(event), pointer = [Event.pointerX(event), Event.pointerY(event)], track = handle, offsets;
        if (track == this.track) {
          offsets = this.track.cumulativeOffset();
          this.event = event;
          this.setValue(this.translateToValue((this.isVertical() ? pointer[1] - offsets[1] : pointer[0] - offsets[0]) - (this.handleLength / 2)));
          offsets = this.activeHandle.cumulativeOffset();
          this.offsetX = (pointer[0] - offsets[0]);
          this.offsetY = (pointer[1] - offsets[1]);
        } else {
          // find the handle (prevents issues with Safari)
          while ((this.handles.indexOf(handle) == -1) && handle.parentNode) {
            handle = handle.parentNode;
          }

          if (this.handles.indexOf(handle) != -1) {
            this.activeHandle = handle;
            this.activeHandleIdx = this.handles.indexOf(this.activeHandle);
            this.updateStyles();

            offsets = this.activeHandle.cumulativeOffset();
            this.offsetX = (pointer[0] - offsets[0]);
            this.offsetY = (pointer[1] - offsets[1]);
          }
        }
      }
      Event.stop(event);
    }
  },
  update: function(event) {
    if (this.active) {
      this.dragging = true;
      this.draw(event);
      if (Prototype.Browser.WebKit) {
        window.scrollBy(0, 0);
      }
      Event.stop(event);
    }
  },
  draw: function(event) {
    var offsets = this.track.cumulativeOffset(), pointer = [Event.pointerX(event) - this.offsetX - offsets[0], Event.pointerY(event) - this.offsetY - offsets[1]];
    this.event = event;
    this.setValue(this.translateToValue(this.isVertical() ? pointer[1] : pointer[0]));
    if (this.initialized && this.options.onSlide) {
      this.options.onSlide(this.values.length > 1 ? this.values : this.value, this);
    }
  },
  endDrag: function(event) {
    if (this.active && this.dragging) {
      this.finishDrag(event, true);
      Event.stop(event);
    }
    this.active = this.dragging = false;
  },
  finishDrag: function(event, success) {
    this.active = this.dragging = false;
    this.updateFinished();
  },
  updateFinished: function() {
    if (this.initialized && this.options.onChange) {
      this.options.onChange(this.values.length > 1 ? this.values : this.value, this);
    }
    this.event = null;
  }
});
