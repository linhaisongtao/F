package com.dss.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * Created by daisongsong on 2017/5/17.
 */
public class Ui {
    private ChartPanel frame1;
    private UiData mUiData;

    public Ui(UiData uiData) {
        mUiData = uiData;
        initChart();
    }

    private XYDataset createDataset() {  //这个数据集有点多，但都不难理解
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        for (TimeSeries series : mUiData.getTimeSeries()) {
            timeseriescollection.addSeries(series);
        }
        return timeseriescollection;
    }

    public void showChart() {
        JFrame frame = new JFrame("Java数据统计图");
        frame.setLayout(new GridLayout(1, 1, 10, 10));
        frame.add(getChartPanel());    //添加折线图
        frame.setBounds(50, 50, 800, 600);
        frame.setVisible(true);
    }

    public void initChart() {
        XYDataset xydataset = createDataset();
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(mUiData.getChartTitle(), "d", "p", xydataset, true, true, true);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
        frame1 = new ChartPanel(jfreechart, true);
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
    }

    public ChartPanel getChartPanel() {
        return frame1;
    }
}
