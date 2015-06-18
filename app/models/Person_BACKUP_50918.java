package models;

import com.avaje.ebean.Model;
import com.sun.javafx.beans.IDProperty;
import play.data.Form;
import play.mvc.Result;

import javax.persistence.Entity;
import javax.persistence.Id;

<<<<<<< HEAD
// added a hotfix comment
=======
// Added a test comment for testBranch
// testbranch 2
>>>>>>> testBranch

/**
 * Created by johnstrudwick on 09/06/15.
 */
@Entity
public class Person extends Model{


    @Id
    public String id;

    public String name;

}
