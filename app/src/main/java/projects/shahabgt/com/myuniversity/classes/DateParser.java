package projects.shahabgt.com.myuniversity.classes;


import projects.shahabgt.com.myuniversity.models.DateModel;

public class DateParser {

    private static String date;

    public DateParser(String date) {

        DateParser.date = date;

    }

    public static DateModel dateAndTimeParser() {

        DateModel dm = new DateModel();

        String year = date.substring(0 , 4);
        String month = date.substring(5 , 7);
        String day = date.substring(8 , 10);

        String hour = date.substring(11 , 13);
        String min = date.substring(14 , 16);
        String ss = date.substring(17 , 19);

        dm.setYear(Integer.valueOf(year));
        dm.setMonth(Integer.valueOf(month));
        dm.setDay(Integer.valueOf(day));

        dm.setHour(Integer.valueOf(hour));
        dm.setMin(Integer.valueOf(min));
        dm.setSec(Integer.valueOf(ss));


        int gy = dm.getYear();
        int gm = dm.getMonth();
        int gd = dm.getDay();
        int gh = dm.getHour();
        int gmin = dm.getMin();
        int gs = dm.getSec();

        int[] g_d_m = {0,31,59,90,120,151,181,212,243,273,304,334};
        int jy;
        int jh , jmin;

        int mdif = 30;
        int hdif;

        if(gy>1600){
            jy=979;
            gy-=1600;
        }else{
            jy=0;
            gy-=621;
        }
        int gy2 = (gm > 2)?(gy + 1):gy;
        int days = (365 * gy) + (gy2 + 3) / 4 - (gy2 + 99) / 100 + (gy2 + 399) / 400 - 80 + gd + g_d_m[gm - 1];
        jy += 33 * days / 12053;
        days %= 12053;
        jy += 4 * days / 1461;
        days %= 1461;
        if(days > 365){
            jy+= (days-1)/365;
            days=(days-1)%365;
        }
        int jm = (days < 186)?1 + days / 31 :7 + (days - 186) / 30;
        int jd = 1 + ((days < 186)?(days % 31):((days - 186) % 30));


        if (jm < 6){
            hdif = 4;
            jh = gh - hdif;
            jmin = gmin - 30;
        }else {
            hdif = 3;
            jh = gh - hdif;
            jmin = gmin - 30;
        }

        if (jmin < 0){
            jmin +=60;
        }


        int[] out = {jy,jm,jd,jh,jmin,gs};
        DateModel temp = new DateModel(out[0], out[1], out[2], out[3], out[4], out[5]);

        return temp;

    }

}
