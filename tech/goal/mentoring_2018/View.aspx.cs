using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.Odbc;
using System.Data.SqlClient;

public partial class Board_View : System.Web.UI.Page
{
    private string board_no;
    public bool isOwn { get; set; }

    protected void Page_Load(object sender, EventArgs e)
    {
        DataBind();

        board_no = Request.QueryString["no"];

        // 페이지가 맨처음 로드가 아닌 경우, 조회수 로직은 스킵
        if (this.IsPostBack) {
            return;
        }
        ClientScript.RegisterStartupScript(typeof(Page), "No",
           "<script language=JavaScript>alert('1');</script>");


        OdbcConnection odConn = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);
        odConn.Open();

        OdbcCommand cmd = new OdbcCommand();
        cmd.Connection = odConn;
        cmd.CommandText = "UPDATE board_trevel SET HITS = HITS+1 WHERE BOARD_NO = " + board_no;
        cmd.CommandType = CommandType.Text;
        cmd.ExecuteNonQuery();
        
        cmd.CommandText = "SELECT * FROM board_trevel WHERE BOARD_NO = " + board_no;
        cmd.CommandType = CommandType.StoredProcedure;
        // 데이터어뎁터
        OdbcDataAdapter da = new OdbcDataAdapter(cmd);
        // 데이터 셋
        DataSet ds = new DataSet();
        // Fill()
        da.Fill(ds);
        // 바인딩
        DataRow dr = ds.Tables[0].Rows[0];
        TITLE.Text = dr["TITLE"].ToString();
        HITS.Text = dr["HITS"].ToString();
        WRITE_ID.Text = dr["WRITE_ID"].ToString();
        WRITE_DT.Text = dr["WRITE_DT"].ToString();
        CONTENTS.Text = dr["CONTENTS"].ToString();
        isOwn = WRITE_ID.Text.Equals(Session["loginID"]);
        btnDel.Enabled = isOwn;
        btnEdit.Enabled = isOwn;
        //종료
        odConn.Close();

        DisplayReply();
    }
    
    private void DisplayReply()
    {
        // 커넥션
        OdbcConnection con = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);
        // 커멘드
        OdbcCommand cmd = new OdbcCommand("SELECT * FROM board_reply WHERE BOARD_NO = "+ board_no + " ORDER BY WRITE_DT DESC", con);
        cmd.CommandType = CommandType.StoredProcedure;
        // 데이터어뎁터
        OdbcDataAdapter da = new OdbcDataAdapter(cmd);
        // 데이터 셋
        DataSet ds = new DataSet();
        // Fill()
        da.Fill(ds);
        // 바인딩
        this.ctlReplyList.DataSource = ds.Tables[0];
        this.ctlReplyList.DataBind();
        //종료
        con.Close();
    }
    

    protected void btnList_Click(object sender, EventArgs e)
    {
        // 리스트 페이지로 이동
        Response.Redirect("Community.aspx");
    }
    protected void btnReplyAdd_Click(object sender, EventArgs e)
    {
        OdbcConnection odConn = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);
        odConn.Open();

        OdbcCommand cmd = new OdbcCommand();
        cmd.Connection = odConn;

        string content = this.replContents.Text;

        cmd.CommandText = "INSERT INTO board_reply(BOARD_NO, CONTENTS, WRITE_ID, WRITE_DT) VALUES("
            + board_no + ", '" + content + "', '" + Session["loginID"] + "', NOW())";
        cmd.CommandType = CommandType.Text;
        cmd.ExecuteNonQuery();

        odConn.Close();

        DisplayReply();
    }
    protected void btnMod_Click(object sender, EventArgs e)
    {
        if (!isOwn) {
            ClientScript.RegisterStartupScript(typeof(Page), "No",
           "<script language=JavaScript>alert('본인만 수정이 가능합니다."+ WRITE_ID.Text +""+ Session["loginID"] + "');</script>");
            return;
        }
        string strUrl =
            String.Format("Write.aspx?no={0}"
                , board_no
            );
        Response.Redirect(strUrl);
    }
    protected void btnDel_Click(object sender, EventArgs e)
    {
        if (!isOwn)
        {
            ClientScript.RegisterStartupScript(typeof(Page), "No",
           "<script language=JavaScript>alert('본인만 삭제가 가능합니다.');</script>");
            return;
        }
        OdbcConnection odConn = new OdbcConnection(ConfigurationManager.ConnectionStrings["MySQLConnStr"].ConnectionString);
        odConn.Open();

        OdbcCommand cmd = new OdbcCommand();
        cmd.Connection = odConn;

        string content = this.replContents.Text;

        cmd.CommandText = "DELETE FROM board_trevel WHERE BOARD_NO = " + board_no;
        cmd.CommandType = CommandType.Text;
        cmd.ExecuteNonQuery();

        odConn.Close();
        btnList_Click(null, null);
    }
}