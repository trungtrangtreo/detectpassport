package vn.spaceshare.demo.customview;

import vn.spaceshare.demo.tflite.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
    public void setResults(final List<Recognition> results);
}

