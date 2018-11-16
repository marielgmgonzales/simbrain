/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.plot.timeseries;

import org.simbrain.plot.ChartCouplingListener;
import org.simbrain.plot.ChartDataSource;
import org.simbrain.plot.ChartListener;
import org.simbrain.workspace.AttributeContainer;
import org.simbrain.workspace.Workspace;
import org.simbrain.workspace.WorkspaceComponent;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents time series data.
 */
public class TimeSeriesPlotComponent extends WorkspaceComponent {

    /**
     * The data model.
     */
    private final TimeSeriesModel model;

    /**
     * Create new time series plot component.
     *
     * @param name name
     */
    public TimeSeriesPlotComponent(String name) {
        super(name);
        model = new TimeSeriesModel(() -> getWorkspace().getTime());
        addListener();
    }

    /**
     * Creates a new time series component from a specified model. Used in
     * deserializing.
     *
     * @param name  chart name
     * @param model chart model
     */
    public TimeSeriesPlotComponent(String name, TimeSeriesModel model) {
        super(name);
        this.model = model;
        model.setTimeSupplier(() -> getWorkspace().getTime());
        addListener();
    }

    /**
     * Add chart listener to model.
     */
    private void addListener() {
        model.addListener(new ChartListener() {
            public void dataSourceAdded(ChartDataSource source) {
                fireAttributeContainerAdded(source);
            }

            public void dataSourceRemoved(ChartDataSource source) {
                fireAttributeContainerRemoved(source);
            }
        });
    }

    @Override
    public void setWorkspace(Workspace workspace) {
        // This is a bit of a hack because the workspace is not available in the constructor.
        super.setWorkspace(workspace);
        workspace.getCouplingManager().
            addCouplingListener(new ChartCouplingListener(getWorkspace(), model, "TimeSeries"));
    }

    @Override
    public List<Object> getAttributeContainers() {
        List<Object> models = new ArrayList<Object>();
        models.add(model);
        models.addAll(model.getTimeSeriesList());
        return models;
    }

    /**
     * @return the model.
     */
    public TimeSeriesModel getModel() {
        return model;
    }

    @Override
    public AttributeContainer getObjectFromKey(String objectKey) {
        if (objectKey.equals(model.getName())) {
            return model;
        } else {
            Optional<TimeSeriesModel.TimeSeries> timeSeries = model.getTimeSeries(objectKey);
            if (timeSeries.isPresent()) {
                return timeSeries.get();
            }
        }
        return null;
    }

    /**
     * Standard method call made to objects after they are deserialized. See:
     * http://java.sun.com/developer/JDCTechTips/2002/tt0205.html#tip2
     * http://xstream.codehaus.org/faq.html
     *
     * @return Initialized object.
     */
    private Object readResolve() {
        return this;
    }

    /**
     * Opens a saved time series plot.
     *
     * @param input  stream
     * @param name   name of file
     * @param format format
     * @return bar chart component to be opened
     */
    public static TimeSeriesPlotComponent open(final InputStream input, final String name, final String format) {
        TimeSeriesModel dataModel = (TimeSeriesModel) TimeSeriesModel.getXStream().fromXML(input);
        return new TimeSeriesPlotComponent(name, dataModel);
    }

    @Override
    public void save(final OutputStream output, final String format) {
        TimeSeriesModel.getXStream().toXML(model, output);
    }

    @Override
    public boolean hasChangedSinceLastSave() {
        return false;
    }

    @Override
    public void closing() {
    }

    @Override
    public void update() {
        model.update();
    }

    @Override
    public String getXML() {
        return TimeSeriesModel.getXStream().toXML(model);
    }

}
