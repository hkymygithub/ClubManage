package clubmanage.ui.manage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import clubmanage.model.Club;
import clubmanage.model.User;
import clubmanage.ui.CheckActivity;
import clubmanage.ui.CheckResult;
import clubmanage.ui.ClubMemberManage;
import clubmanage.ui.CreateActivity;
import clubmanage.ui.CreateClub;
import clubmanage.ui.ManageClub;
import clubmanage.ui.R;
import clubmanage.util.ClubManageUtil;

import static android.app.Activity.RESULT_OK;

public class Manage_Fragement extends Fragment implements View.OnClickListener {
    private RelativeLayout create;
    private Button result;
    private LinearLayout my_club_manage;
    private LinearLayout club_audit;
    private LinearLayout activity_audit;
    private Integer clubid = null;
    private boolean haveCLubAppli;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            clubid=(Integer) msg.obj;
            if(User.currentLoginUser.getUser_category().equals("社团管理员")){
                club_audit.setVisibility(View.VISIBLE);
                activity_audit.setVisibility(View.VISIBLE);
                result.setVisibility(View.VISIBLE);
            }else {
                if (clubid!=null){
                    result.setVisibility(View.VISIBLE);
                    my_club_manage.setVisibility(View.VISIBLE);
                }
            }

        }
    };
    private Handler handler2=new Handler(){
        public void handleMessage(Message msg){
            haveCLubAppli=(boolean)msg.obj;
            if (haveCLubAppli==true||User.currentLoginUser.getUser_category().equals("社团管理员")){
                create.setVisibility(View.INVISIBLE);
            }else if(haveCLubAppli==false||clubid==null){
                create.setVisibility(View.VISIBLE);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.manage,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout member=getActivity().findViewById(R.id.member);
        LinearLayout apply_activity=getActivity().findViewById(R.id.apply_activity);
        LinearLayout edit_activity=getActivity().findViewById(R.id.edit_activity);
        LinearLayout club_manage=getActivity().findViewById(R.id.club_manage);
        LinearLayout club_apply_audit=getActivity().findViewById(R.id.club_apply_audit);
        LinearLayout club_destroy_audit=getActivity().findViewById(R.id.club_destroy_audit);
        LinearLayout activity_apply_audit=getActivity().findViewById(R.id.activity_apply_audit);
        LinearLayout activity_destroy_audit=getActivity().findViewById(R.id.activity_destroy_audit);
        member.setOnClickListener(this);
        apply_activity.setOnClickListener(this);
        edit_activity.setOnClickListener(this);
        club_manage.setOnClickListener(this);
        club_apply_audit.setOnClickListener(this);
        club_destroy_audit.setOnClickListener(this);
        activity_apply_audit.setOnClickListener(this);
        activity_destroy_audit.setOnClickListener(this);

        my_club_manage=getActivity().findViewById(R.id.my_club_manage);
        club_audit=getActivity().findViewById(R.id.club_audit);
        activity_audit=getActivity().findViewById(R.id.activity_audit);
        create =getActivity().findViewById(R.id.manage_btn1);
        create.setOnClickListener(this);
        result=getActivity().findViewById(R.id.manage_head_btn1);
        result.setOnClickListener(this);
        initPage();
    }

    private void initPage(){
        initItem();
        ifHaveClubAppli();
        searchClub();
    }

    private void initItem(){
        if(my_club_manage.getVisibility()==View.VISIBLE) my_club_manage.setVisibility(View.GONE);
        if(club_audit.getVisibility()==View.VISIBLE) club_audit.setVisibility(View.GONE);
        if(activity_audit.getVisibility()==View.VISIBLE) activity_audit.setVisibility(View.GONE);
        if(create.getVisibility()==View.VISIBLE) create.setVisibility(View.INVISIBLE);
        if(result.getVisibility()==View.VISIBLE) result.setVisibility(View.INVISIBLE);
    }

    private void ifHaveClubAppli(){
        new Thread(){
            @Override
            public void run() {
                boolean haveClubAppli= ClubManageUtil.applicationManage.ifHaveClubAppli(User.currentLoginUser.getUid());
                Message message=new Message();
                message.obj=haveClubAppli;
                handler2.sendMessage(message);
            }
        }.start();
    }

    private void searchClub(){
        new Thread(){
            @Override
            public void run() {
                Integer clubid= ClubManageUtil.clubManage.searchClubIdByProprieter(User.currentLoginUser.getUid());
                Message message=new Message();
                message.obj=clubid;
                handler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.member:
                Intent intent1=new Intent(getContext(), ClubMemberManage.class);
                intent1.putExtra ( "clubid", clubid);
                intent1.putExtra ( "iscap", true);
                startActivity(intent1);
                break;
            case R.id.apply_activity:
                Intent intent2=new Intent(getContext(), CreateActivity.class);
                intent2.putExtra ( "clubid", clubid);
                startActivity(intent2);
                break;
            case R.id.edit_activity:
                break;
            case R.id.club_manage:
                Intent intent4=new Intent(getContext(), ManageClub.class);
                intent4.putExtra ( "clubid", clubid);
                startActivity(intent4);
                break;
            case R.id.club_apply_audit:
                break;
            case R.id.club_destroy_audit:
                break;
            case R.id.activity_apply_audit:
                Intent intent7=new Intent(getContext(), CheckActivity.class);
                startActivity(intent7);
                break;
            case R.id.activity_destroy_audit:
                break;
            case R.id.manage_btn1:
                Intent intent9=new Intent(getContext(), CreateClub.class);
                startActivityForResult(intent9,10);
                break;
            case R.id.manage_head_btn1:
                Intent intent10=new Intent(getContext(), CheckResult.class);
                startActivity(intent10);
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        super.onHiddenChanged(hidden);
        if (hidden) {
            return;
        }else{
            initPage();
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    create.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}