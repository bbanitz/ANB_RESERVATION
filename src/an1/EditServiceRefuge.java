/*
 * EditServiceRefuge.java
 *
 * Created on 12 mai 2009, 10:03:51
 * Copyright bbernard
 */
package an1;

//import com.icesoft.faces.component.jsfcl.data.SelectInputDateBean;
//import com.sun.rave.faces.converter.SqlDateConverter;
//import com.sun.rave.web.ui.appbase.AbstractPageBean;
//import javax.faces.FacesException;
import javax.faces.convert.DateTimeConverter;

import com.icesoft.faces.component.selectinputdate.SelectInputDate;


/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 */
public class EditServiceRefuge {
    private SelectInputDate selectInputDate1Bean = new SelectInputDate();

    public SelectInputDate getSelectInputDate1Bean() {
        return selectInputDate1Bean;
    }

    public void setSelectInputDate1Bean(SelectInputDate sidb) {
        this.selectInputDate1Bean = sidb;
    }
    private SelectInputDate selectInputDate2Bean = new SelectInputDate();

    public SelectInputDate getSelectInputDate2() {
        return selectInputDate2Bean;
    }

    public void setSelectInputDate2Bean(SelectInputDate sidb) {
        this.selectInputDate2Bean = sidb;
    }
    private DateTimeConverter sqlDateConverter1 = new DateTimeConverter();

    public DateTimeConverter getSqlDateConverter1() {
        return sqlDateConverter1;
    }

    public void setSqlDateConverter1(DateTimeConverter sdc) {
        this.sqlDateConverter1 = sdc;
    }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public EditServiceRefuge() {
    }


}

