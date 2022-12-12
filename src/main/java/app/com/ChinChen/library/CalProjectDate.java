package app.com.ChinChen.library;

import app.com.ChinChen.domain.ChangePrice;
import app.com.ChinChen.domain_temp.Project_Data;

import java.util.Date;
import java.util.List;

public class CalProjectDate {
    public static void getPtojectDate(Date dailyDate, Project_Data projectData) {
        //1.日曆天 project.endDate-project.beginDate
        projectData.setCalendarDay(DateCal.dateDiff(projectData.getEndDate(), projectData.getBeginDate()));
        //2.累計工期
        projectData.setCumulativeDay(DateCal.dateDiff(dailyDate, projectData.getBeginDate()));
        //3.剩餘工期
        projectData.setRemainingDay(projectData.getCalendarDay() - projectData.getCumulativeDay());
        //4.更變更後契約金額
        int bidAmount = projectData.getBidAmount();
        int sum = 0;
        List<ChangePrice> list = projectData.getChangePriceList();
        if (list != null) {
            for (ChangePrice changePrice : list) {
                sum = sum + changePrice.getChangePrice();
            }
        }
        projectData.setChangePrice(bidAmount + sum);
    }
}
