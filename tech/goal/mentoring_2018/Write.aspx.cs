using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.Odbc;

public partial class Basic_Write : System.Web.UI.Page
{
    private string board_no = null;
    public bool isOwn = false;

    private void ReadData()
    {
        OdbcConnection odConn = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);

        odConn.Open();

        DataSet ds = new DataSet();
        OdbcDataAdapter adp = new OdbcDataAdapter();

        string query = "SELECT * FROM board_trevel ";
        board_no = Request.QueryString["no"];
        if (board_no != "")
        {
            query += "WHERE BOARD_NO = " + board_no;
        }

        adp.SelectCommand = new OdbcCommand("SELECT * FROM board_trevel", odConn);
        adp.Fill(ds);

        odConn.Close();

        if (board_no != "")
        {
            if (ds.Tables[0].Rows.Count > 0)
            {
                isOwn = ds.Tables[0].Rows[0]["WRITE_ID"].ToString().Equals(Session["loginID"]);
                if (!isOwn)
                {
                    ClientScript.RegisterStartupScript(typeof(Page), "No",
                   "<script language=JavaScript>alert('본인만 수정이 가능합니다.');</script>");

                    btnList_Click(null, null);
                    return;
                }
            }
            
        }
    }
 
    protected void Page_Load(object sender, EventArgs e)
    {
        ReadData();
        Page.SetFocus("txtTitle");
    }

    protected void btnWrite_Click(object sender, EventArgs e)
    {
        string title = this.txtTitle.Text;
        string content = this.txtContent.Text;

        OdbcConnection odConn = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);
        odConn.Open();
        string strSql = "";

        if (board_no != null)
        {
            strSql = "UPDATE board_trevel SET TITLE = '" + title + "', CONTENTS = '" + content + "' WHERE BOARD_NO = " + board_no;
        }
        else {
            strSql = "INSERT INTO board_trevel(TITLE, CONTENTS, WRITE_ID, WRITE_DT) Values('"
            + title + "','" + content + "','" + Session["loginID"] + "', NOW() )"; //
        }
        //커맨드 객체
        OdbcCommand objCmd = new OdbcCommand();
        objCmd.Connection = odConn;
        objCmd.CommandText = strSql;  //SQL INSERT 쿼리
        objCmd.CommandType = CommandType.Text;
        objCmd.ExecuteNonQuery();
        //종료
        odConn.Close();
        
        //i = Convert.ToInt32(seq);
        //i++;
        //Session["seq"] = i;
        //ReadData();
        btnList_Click(null, null); //리스트 페이지로 이동
    }
    protected void btnList_Click(object sender, EventArgs e)
    {
        // 리스트 페이지로 이동
        Response.Redirect("Community.aspx");
    }
}