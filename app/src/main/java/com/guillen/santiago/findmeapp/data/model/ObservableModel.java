package com.guillen.santiago.findmeapp.data.model;

public class ObservableModel<T> {
    private T dataModel;
    private boolean isAdded;
    private boolean isModified;
    private boolean isRemoved;

    public ObservableModel() {
        this.isAdded = false;
        this.isModified = false;
        this.isRemoved = false;
    }

    public T getDataModel() {
        return dataModel;
    }

    public void setDataModel(T dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
