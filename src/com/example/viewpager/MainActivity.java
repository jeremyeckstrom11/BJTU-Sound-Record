package com.example.viewpager;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.viewpager.NotificationExtend;
import com.example.viewpager.DirectoryPicker;
import android.support.v4.widget.DrawerLayout;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
public class MainActivity extends Activity implements OnClickListener{
	/*
	 * Record page Record page Record page Record page Record page Record page Record page
	 *  */
	private Button start_record; //��ʼ¼����
	private MediaRecorder myRecorder;// ��Ƶ�ļ������ַ
	/* 
	 * Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw Draw
	 * */
	private DrawerLayout mDrawerLayout = null;
	private Button searchFile; //������
	private EditText searchFileName;//�����������ҵ��ļ���
	private ListView listView_record,listView_record1;
	private String pathtodelete;
	private Button delete;
	private TextView filename_page2;
	ShowRecorderAdpter showRecord;
	String[] listFile_record = null;
	String[] cannotFindWord = null;//�Ҳ����ļ�
	String[] notFoundRecordWord = null;//�Ҳ����ļ�
	String[] continueSearchWord = null;//��������
	String[] gotoRecordingWord = null;//ǰ��¼��
	String cannotFind;//�Ҳ����ļ�
	String notFoundRecord;//�Ҳ����ļ�
	String continueSearch;//��������
	String gotoRecording;//ǰ��¼��
	String pleaseInput;//������
	String inputName;  //��������
	String ok;//ȷ��
	String cancel; //ȡ��
	String save;//����
	String deleteWord;//�Ƿ�ɾ��
	String changeName;//������
	/* 
	 * Play Play Play Play Play Play Play Play Play Play Play Play Play Play Play Play Play
	 * */
	private MediaPlayer myPlayer;// ¼��
	TextView filetimeTextview;  //¼��ʱ��
	TextView seekBartimeTextview;
	SeekBar seekBar;  //������
	private Boolean paused = false;
	/* 
	 * ViewPager ViewPager ViewPager ViewPager ViewPager ViewPager ViewPager ViewPager
	 * */
	private ViewPager mPager;//page contents
	private List<View> listViews;//tab  list
	private TextView text1,text3;
	private ImageView cursor;
	private int offset = 0;//����Ƭƫ����
	private int currIndex = 0;//present page
	private int bmpW;//width of animation
	/* 
	 * File File File File File File File File File File File File File File File File
	 * */
	private String path;
	private String paths = path;
	private File saveFilePath;
	private String filetoPlay;
	private int positionoffile;
	File file;
	File picName;
	/*
	 * Theme and Language Theme and Language Theme and Language Theme and Language Theme and Language
	 */
	Boolean themeFlag = true;//��������ĸ�����־
	String[] inputNameDialog = null; //��������
	String[] okDialog = null; //ȷ��
	String[] cancelDialog = null; //ȡ��
	String[] saveDialog = null; //����
	String[] deleteWordDialog = null; //�Ƿ�ɾ��
	String[] changeThemeWord = null;//��������
	String[] changeLanguageWord = null;//��������
	String[] text1Word = null;//#record
	String[] text3Word = null;//#play
	String[] changeNameWord = null;//������
	String[] changeSave = null;//�޸Ĵ洢·��
	/*
	 * Volume Volume Volume Volume Volume Volume Volume Volume Volume Volume Volume Volume
	 */
	private Button downButton;//��������
	private Button upButton;//��������
    private AudioManager audioMa;
	private int volume=0;
	/*
	 * Others
	 */
	private NotificationExtend notification;
	private int recordflag=0;
	int playPosition = 0;
	int listPosition = 0;
	AlertDialog aler = null;
	String[] b = null;
    final static String state = Environment.getExternalStorageState();
	private TextView playName;
	private Button plays,stop,pause,buttonlistofRecord,buttonlistofRecord1;
	View view;
	String[] aboutUsString = null;//��������
	String[] pleaseInputWord = null;//������
	String languageFlag = "Chinese";//�������Ǳ�־λ
	LinearLayout allbackground;//����
	/*
	 * Update SeekBar Update SeekBar Update SeekBar Update SeekBar Update SeekBar Update SeekBar
	 */
    Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch (msg.what){
		      case 0:
		        //���½���
		        int positionofPlay = myPlayer.getCurrentPosition(); //��ȡ��ǰλ��  	          
		        int time = myPlayer.getDuration();   //��ʱ��
		     
		        
		        filetimeTextview.setText(getDurationTime(time));  //��ʾ��Ƶʱ�䳤��
		        seekBartimeTextview.setText(getDurationTime(positionofPlay));//��ʾʱ�������
		        int max = seekBar.getMax();  //���������ֵ
		        seekBar.setProgress(positionofPlay * max / time);  
		        break;
		    default:
		      break;
		      }
			super.handleMessage(msg);
		}
		
		
	};
	/*
	 * init Player init Player init Player init Player init Player init Player init Player
	 */
	  void init(){
	  	    try {
	  	      final int milliseconds = 100;
	  	      new Thread(){
	  	        @Override
	  	        public void run(){
	  	          while(true){  
	  	        try {  
	  	            sleep(milliseconds);  
	  	        } catch (InterruptedException e) {  
	  	            // TODO Auto-generated catch block  
	  	            e.printStackTrace();  
	  	        }  
	  	        mHandler.sendEmptyMessage(0);  
	  	          }  
	  	        }
	  	      }.start();
	  	    } catch (Exception e) {
	  	      // TODO Auto-generated catch block
	  	      e.printStackTrace();
	  	    }
	  	    myPlayer.start();
	  	  }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw);
        if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				path = Environment.getExternalStorageDirectory()
						.getCanonicalPath().toString()
						+ "/XIONGRECORDERS";
				File files = new File(path);
				//Toast.makeText(getApplicationContext(),"·���ǣ�" + path, Toast.LENGTH_SHORT).show();
				if (!files.exists()) {
					files.mkdir();
				}
				listFile_record = files.list();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        InitTextView();
        InitImageView();
        InitViewPager();
        notification = new NotificationExtend(this);
        myPlayer = new MediaPlayer();
		myRecorder = new MediaRecorder();
		showRecord = new ShowRecorderAdpter();
		start_record = (Button)listViews.get(0).findViewById(R.id.start_stop_record);
		plays = (Button) listViews.get(1).findViewById(R.id.playActivity_play);
		pause = (Button) listViews.get(1).findViewById(R.id.playActivity_pause);
		searchFile = (Button) findViewById(R.id.searchFile);
		downButton = (Button) listViews.get(1).findViewById(R.id.downVoic);
        upButton = (Button) listViews.get(1).findViewById(R.id.upVoic);
        buttonlistofRecord = (Button) listViews.get(0).findViewById(R.id.filelistOfrecords);
		buttonlistofRecord1 = (Button) listViews.get(1).findViewById(R.id.list_right);
		listView_record = (ListView)findViewById(R.id.list);
		listView_record1 = (ListView)findViewById(R.id.list1);
		filetimeTextview = (TextView) listViews.get(1).findViewById(R.id.playActivity_filetime);  //¼��ʱ��
		seekBartimeTextview = (TextView) listViews.get(1).findViewById(R.id.playActivity_seekBartime);
		playName = (TextView) listViews.get(1).findViewById(R.id.playActivity_filename);
		seekBar = (SeekBar)listViews.get(1).findViewById(R.id.seekBar);
		searchFileName = (EditText) findViewById(R.id.searchFileName);
		
		Resources res = this.getResources();  //ȡ��Ӣ������
	    inputNameDialog = res.getStringArray(R.array.inputName); //��������
		okDialog = res.getStringArray(R.array.ok);	//ok
		cancelDialog = res.getStringArray(R.array.cancel);	//cancel
		saveDialog = res.getStringArray(R.array.save); 		//save
		deleteWordDialog = res.getStringArray(R.array.deleteWord);  
		changeThemeWord = res.getStringArray(R.array.changeThemeWord);
		changeLanguageWord = res.getStringArray(R.array.changeLanguageWord);
		text1Word = res.getStringArray(R.array.text1);
		text3Word = res.getStringArray(R.array.text3);
		changeNameWord = res.getStringArray(R.array.changeNameWord);
		changeSave = res.getStringArray(R.array.saveLocation);

		cannotFindWord = res.getStringArray(R.array.cannotFindFile);
		notFoundRecordWord = res.getStringArray(R.array.notFoundRecord);
		continueSearchWord = res.getStringArray(R.array.continueSearch);
		gotoRecordingWord = res.getStringArray(R.array.gotoRecording);
		aboutUsString = res.getStringArray(R.array.aboutUs);
		pleaseInputWord = res.getStringArray(R.array.pleaseInput);
		allbackground = (LinearLayout) findViewById(R.id.allbackground);  //����
		
		inputName = inputNameDialog[0];
		ok = okDialog[0];
		cancel = cancelDialog[0];
		save = saveDialog[0];
		deleteWord = deleteWordDialog[0];
		changeName = changeNameWord[0];	
		cannotFind = cannotFindWord[0];
		notFoundRecord = notFoundRecordWord[0];
		continueSearch = continueSearchWord[0];
		gotoRecording = gotoRecordingWord[0];
		pleaseInput = pleaseInputWord[0];
		selectlanguage(isZh());  //����ϵͳ���õ����Խ���ѡ������
		//listView_record.addHeaderView(searchText,null,false);
		//�����ʼ�� Object initialization
        audioMa = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //���ó�ʼ���ֻ����� Set the initial mobile phone volume
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
       
        // ���ó�ʼ������ģʽ Set the initial voice patterns
        int mode=audioMa.getRingerMode();
        start_record.setOnClickListener( this);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		if (listFile_record != null) {
			listView_record.setAdapter(showRecord);
			listView_record1.setAdapter(showRecord);
		}
		/*
		 * OnClickListener OnClickListener OnClickListener OnClickListener OnClickListener
		 */
		//search file
		searchFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String sfilename = searchFileName.getText().toString().trim();//��ȡ�ؼ���
				if(sfilename.equals("")){
					searchFileName.setText(pleaseInput);
				}else
				searchFile(sfilename);
				// ˢ��ListView
				showRecord.notifyDataSetChanged();
			}
		});
		//the left list
		 buttonlistofRecord.setOnClickListener(new OnClickListener()
	        {

	            @Override
	            public void onClick(View v)
	            {
	                // ��ť���£��������
	            	showRecord.notifyDataSetChanged();
	            	//Toast.makeText(getApplicationContext(), "draw", Toast.LENGTH_SHORT).show();
	                mDrawerLayout.openDrawer(Gravity.LEFT);

	            }
	        });
		 //the right list
		 buttonlistofRecord1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showRecord.notifyDataSetChanged();
				mDrawerLayout.openDrawer(Gravity.RIGHT);
			}
		});
        //play button
        plays.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				 TODO Auto-generated method stub
//				playRecord();
//				 Toast.makeText(PlayActivity.this, "time is "+myPlayer.getDuration(), 1000).show();
				init();
				Toast.makeText(getApplicationContext(), "inited filetoPlay:"+filetoPlay, Toast.LENGTH_SHORT).show();
				//myPlayer.start();
				
			}
		});
		//pause button
		pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
					try
						     { 
						        if (paused==false) //��flagΪfalse�����ʾ��ʱ��������״̬Ϊ���ڲ���
						             {
						                 myPlayer.pause();
						                 playPosition = myPlayer.getCurrentPosition();
						                 paused=true;			            
						             }else if(paused==true){				       
						                 myPlayer.seekTo(playPosition);//�ӵ�ǰλ�ÿ�ʼ
						                 myPlayer.start();    //��ʼ����
						                 paused=false;     //��������flagΪfalse
						                    //state.setText("Playing");
						             }
					      } catch (Exception e){			         
					    	  e.printStackTrace();
			        }
			}
		});

		//seekbar
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				//�ֶ����ڽ���
				 int dest = seekBar.getProgress();
			        int time = myPlayer.getDuration();
			        int max = seekBar.getMax();
			        
			        myPlayer.seekTo(time*dest/max);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		 //The volume down Button
        downButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
        //  Turning down the volume one level
                audioMa.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
                
        
            }
        });

    // The volume up Button
        upButton.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
        // ��������������һ��    Turning up the volume one level
                audioMa.adjustVolume(AudioManager.ADJUST_RAISE, 0);
                volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
                
            }
        });
		
    }
    private LayoutInflater LayoutInflater(MainActivity mainActivity) {
		// TODO Auto-generated method stub
		return null;
	}

  //format the total play time
  	 String getDurationTime(int duration){
  		 String durationTime = "";
  		 int seconds = duration /  1000 ;
  		// Toast.makeText(getApplicationContext(), "duration is :"+duration,Toast.LENGTH_SHORT).show();
  		 int minute = seconds / 60 ;
  		 seconds = seconds % 60 ;
  		 DecimalFormat df=new DecimalFormat("00");
  		 durationTime=df.format(minute)+ ":" + df.format(seconds);
  		 return durationTime;
  	 }
  	 
  	 //sort the list
  	 String[] sortListFile(String[] llistfile){
  		 String c[] = new String[llistfile.length];
  		c = new String[llistfile.length];			
		for(int i = 0;i < llistfile.length ; i ++ ){										
			c[i] = llistfile[llistfile.length - 1 - i].trim();											
		}										
		return c;
  		 
  	 }
  	 //judge the language 
  	private boolean isZh() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))//����
            return true;
        else
            return false;
    }
  	 
  	 //select language (menu)
  	 void selectlanguage(Boolean flag){
  		 if(flag){//����
  			inputName = inputNameDialog[1];
			ok = okDialog[1];
			cancel = cancelDialog[1];
			save = saveDialog [1]; 
			deleteWord = deleteWordDialog[1];
			changeName = changeNameWord[1];	
			text1.setText(text1Word[1]);
			text3.setText(text3Word[1]);
			languageFlag = "Chinese";
  		 }else{
  			inputName = inputNameDialog[0];
			ok = okDialog[0];
			cancel = cancelDialog[0];
			save = saveDialog [0]; 
			deleteWord = deleteWordDialog[0];
			changeName = changeNameWord[0];	
			text1.setText(text1Word[0]);
			text3.setText(text3Word[0]);	
			languageFlag = "English";
  		 }
		
	}
 
    //init the ViewPager
    private void InitTextView(){
        text1 = (TextView) findViewById(R.id.text1);
        text3 = (TextView) findViewById(R.id.text3);
        text1.setOnClickListener(new MyOnClickListener(0));
        text3.setOnClickListener(new MyOnClickListener(1));
    }
    private void InitViewPager(){
    	mPager = (ViewPager) findViewById(R.id.vPager);
    	listViews = new ArrayList<View>();
    	LayoutInflater mInflater = getLayoutInflater();
    	listViews.add(mInflater.inflate(R.layout.page1, null));
    	listViews.add(mInflater.inflate(R.layout.page3, null));
    	mPager.setAdapter(new MyPagerAdapter(listViews) );
    	mPager.setOnPageChangeListener(new MyOnPageChangeListener());
			
    }
    //init the silder
    private void InitImageView() {
    	cursor = (ImageView) findViewById(R.id.cursor);
    	bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.delete).getWidth();// ��ȡͼƬ��� get width of image
    	DisplayMetrics dm = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(dm);
    	int screenW = dm.widthPixels;// ��ȡ��Ļ���
        offset = (screenW / 2 - bmpW) / 2;// ����ƫ����
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
    	cursor.setImageMatrix(matrix);// ���ö�����ʼλ�� set the start position
    }
    //To determine whether a name is legal or not
    private Boolean ifnameLegal(String name){
    	int flag = 0;
    	String speace = " ";
    	String dot = ".";
    	Boolean flagtoContinue = true;
    	if(name.length() == 0){
    		flag = 1;
    	}
    	if(name.length()>16){
    		flag = 1;
    	}
    	if(name.indexOf(dot)>=0 || name.indexOf(speace)>=0){
    		flag = 1;
    	}

    	if(flag == 1 ){
    		flagtoContinue = false;
    	}
		return flagtoContinue;
    	
    }
    
    //set the textview onclick��mpager onclick
    public class MyOnClickListener implements OnClickListener{
    	private int index = 0;
    	public MyOnClickListener(int i){
    		index = i;
    	}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
			
		}
    	
    }
    //set the animation between two pages
    public class MyOnPageChangeListener implements OnPageChangeListener {
        int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
        int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
        @Override
            public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    text1.setTextColor(Color.parseColor("#E20001"));
                    text3.setTextColor(Color.parseColor("#E87905"));
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    text1.setTextColor(Color.parseColor("#E87905"));
                    text3.setTextColor(Color.parseColor("#E20001"));
                  }
                break;
            }   	
            currIndex = arg0;
            animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
 
    
    
    //push the page into the list ,the default is the first page����listװ������Ĭ�ϵ�һҳ
    public class MyPagerAdapter extends PagerAdapter{
    	public List<View> mListViews;
    	public MyPagerAdapter(List<View> mListView){
    		this.mListViews = mListView;
    	}
    	@Override
    	public void destroyItem(View arg0,int arg1,Object arg2){
    		((ViewPager) arg0).removeView(mListViews.get(arg1));
    	}
    	
    	@Override
    	public void finishUpdate(View arg0){
    		
    	}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mListViews.size();
		}
		@Override
		public Object instantiateItem(View arg0,int arg1){
			switch(arg1){
				case 0:
					mListViews.get(0).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(getApplicationContext(), "page0", Toast.LENGTH_SHORT).show();
						}
						
					});break;
				case 2:
					mListViews.get(2).setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
						}
					});break;
			}
			((ViewPager) arg0).addView(mListViews.get(arg1),0);
			return mListViews.get(arg1);
			
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==(arg1);
		}
		@Override
   	    public void restoreState(Parcelable arg0, ClassLoader arg1) {
			   
	    }
	    @Override
		public Parcelable saveState() {
			   
		    return null;
			   
	    }
	    @Override		   
	     public void startUpdate(View arg0) {
			   
	     }
    }

    //init the list_record
	class ShowRecorderAdpter extends BaseAdapter {

		@Override
		public int getCount() {
			return listFile_record.length;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;

		}
		@Override
		public View getView(final int postion, View arg1, ViewGroup arg2) {
			View views = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_show_filerecord, null);
			delete = (Button)views.findViewById(R.id.delete);
			filename_page2 = (TextView) views.findViewById(R.id.show_file_name);
			filename_page2.setText(listFile_record[postion]);
			//delete button in the list
			delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
			    	Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
					Builder dialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle(deleteWord)
					.setNegativeButton(cancel, null)
					.setPositiveButton(ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									Toast.makeText(getApplicationContext(), "gggggg", Toast.LENGTH_SHORT).show();
									// TODO Auto-generated method stub
									pathtodelete = path + "/" + listFile_record[postion];
									file = new File(pathtodelete);
									Toast.makeText(getApplicationContext(), file.getName(),Toast.LENGTH_SHORT).show();
									  if(state.equals(Environment.MEDIA_MOUNTED))
								        {
								            if (file.exists())
								            {
								                if (file.isFile())
								                {
								                    file.delete();
								                }
								               
								            }
								            file.delete();
								        }
									  File filelistview = new File(path);
									  listFile_record = sortListFile(filelistview.list());
									  showRecord.notifyDataSetChanged();
								}});
					dialog.show();
					
				}
			});
			//change name in the list
			filename_page2.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					final EditText newFilename = new EditText(MainActivity.this);
					new AlertDialog.Builder(MainActivity.this)  
					.setTitle(changeName)  
					.setIcon(android.R.drawable.ic_dialog_info)  
					.setView(newFilename)  
					.setPositiveButton(ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							if(ifnameLegal(newFilename.getText().toString())){
								String patha;
						    	String newPatha;
						    	
						    	patha = path+ "/"+ listFile_record[postion];//.getText().toString().trim();	
						    	//Toast.makeText(getApplicationContext(), "patha"+patha, Toast.LENGTH_SHORT).show();
							    listFile_record[postion] = newFilename.getText().toString().trim()+ ".amr";		
							    //Toast.makeText(getApplicationContext(), "listFile_record[postion]"+listFile_record[postion], Toast.LENGTH_SHORT).show();
						    	filename_page2.setText(listFile_record[postion]);
						    	//Toast.makeText(getApplicationContext(), "filname_page2"+filname_page2, Toast.LENGTH_SHORT).show();
						    	newPatha = path+"/"+newFilename.getText().toString().trim()+ ".amr";
						    	//Toast.makeText(getApplicationContext(), "newpatha"+newPatha, Toast.LENGTH_SHORT).show();
						    	File file = new File(patha);
						        file.renameTo(new File(newPatha));   
						        showRecord.notifyDataSetChanged();
							}
							else if(!ifnameLegal(newFilename.getText().toString())){
								Toast.makeText(getApplicationContext(), "name is illegal", Toast.LENGTH_SHORT).show();
								
							}
							
						}
						
					})  
					.setNegativeButton(cancel, null).setCancelable(false)
					.show();  
					return false;
				}
			});
			
			//click the name .
			filename_page2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub		
		  	    	myPlayer.release();
		  	    	myPlayer = new MediaPlayer();
		  	    	init();
		  	    	mDrawerLayout.closeDrawer(Gravity.LEFT);
		  	    	mDrawerLayout.closeDrawer(Gravity.RIGHT);
					filetoPlay = listFile_record[postion];
					try {
						myPlayer.setDataSource(path + "/" + filetoPlay);
						myPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);  
				  	      
			  	      // prepare ͨ���첽�ķ�ʽװ��ý����Դ
				  	    myPlayer.prepareAsync();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					playName.setText(filetoPlay);
					int positionofPlay = myPlayer.getCurrentPosition(); //��ȡ��ǰλ��  	          
			        int time = myPlayer.getDuration();   //��ʱ��		        
			        filetimeTextview.setText(getDurationTime(time));  //��ʾ��Ƶʱ�䳤��
			        seekBartimeTextview.setText(getDurationTime(positionofPlay));//��ʾʱ�������
			       // int max = seekBar.getMax();  //��������� 
					text3.performClick();
					//��ת
					
				}
			});
		return views;
	}
 	}
	//the start_stop_record button
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.start_stop_record) {
			if(recordflag==0){
					final EditText filename = new EditText(this);
					Builder alerBuidler = new Builder(this);
					alerBuidler	.setTitle(inputName)
								.setView(filename)
								.setPositiveButton(ok,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										String text = filename.getText().toString();
										if(ifnameLegal(text)){
											try {
												
												myRecorder.release();				
												myRecorder = new MediaRecorder();										
												// ����˷�Դ����												
												myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);									
												// ���������ʽ
												myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);									
												// ���ñ����ʽ
												myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);										
												paths = path+ "/"+ text+ ".amr";			
												saveFilePath = new File(paths);
												myRecorder.setOutputFile(saveFilePath.getAbsolutePath());									
												saveFilePath.createNewFile();										
												myRecorder.prepare();							
												// ��ʼ¼��
												myRecorder.start();
												//start_record.setBackgroundResource(R.drawable.record_cannothit);
												start_record.setBackgroundResource(R.drawable.record_tostop);
												//start_record.setEnabled(false);										
												aler.dismiss();
												// ���¶�ȡ �ļ�
												File files = new File(path);
												listFile_record = sortListFile(files.list());
												// ˢ��ListView
												showRecord.notifyDataSetChanged();
												
												//stop_record.setEnabled(true); //ֹͣ�����Ե��
											} catch (Exception e) {
												e.printStackTrace();
											}
											recordflag = 1;
										}
										else if(!ifnameLegal(text)){
											//new AlertDialog.Builder(this).setTitle("warning").setMessage("the name has illegal characters").setPositiveButton("ȷ��",null).show();
											Toast.makeText(getApplicationContext(), "ifnameLegal", Toast.LENGTH_SHORT).show();
											aler.dismiss();
										}
									}
								});
						aler = alerBuidler.create();
						aler.setCanceledOnTouchOutside(false);
						aler.show();
			}
			else if(recordflag==1){
				if (saveFilePath.exists() && saveFilePath != null) {
					myRecorder.stop();
					myRecorder.release();
					// �ж��Ƿ񱣴� �����������ɾ��
					new AlertDialog.Builder(this)
							.setTitle(save)
							.setPositiveButton(ok, null)
							.setNegativeButton(cancel,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,
												int which) {
											saveFilePath.delete();
											// ���¶�ȡ �ļ�
											File files = new File(path);
											listFile_record = sortListFile(files.list());
											// ˢ��ListView
											showRecord.notifyDataSetChanged();
										}
									}).show();

				}
//				start.setText("¼��");
				start_record.setBackgroundResource(R.drawable.record_record);
				//start_record.setEnabled(true);
//				onDestroy();
				recordflag = 0;
			}
		} else {
		}
	}
	@Override
	protected void onDestroy() {
		// �ͷ���Դ
		if (myPlayer.isPlaying()) {
			myPlayer.stop();
			myPlayer.release();
		}
		myPlayer.release();
		myRecorder.release();
		super.onDestroy();
	}
	
	private void searchFile(String keyword){   
	    /*File f = new File("/")ָ�ڵ�ǰ�̷�·����*/  
	    /*listFiles()���԰�Ŀ¼������ļ�����Ŀ¼�������*/  
	    File[] files = new File(path + "/").listFiles(); 
	    String result = "";
	    String sfile = "";
	    int pos = 0;
	    	 
	    for (File f : files)  
	    {  
	      //�ж��ļ���f���Ƿ����keyword   
	    	
	    	if (f.getName().indexOf(keyword) >= 0)  
	      {  
	        //f.getPath()�����ļ���·��  
	        //result += f.getPath() + "/n"; 
	    		result += f.getPath() +"/n";
	        sfile = f.getName();	
	        //����˳�򣬼����ҵ����ļ�������ǰ
	        for(int i = 0 ; i < listFile_record.length ; i++){
	        	if(sfile.equals(listFile_record[i])){
	        		
	        		pos = i;
	        	}
	        }
	        for(int i = pos; i > 0 ; i--){
	        	listFile_record[i] = listFile_record[i-1];
	        }
	        listFile_record[0] = sfile;
	      }
	    }   
	    if(result.equals("")){
	    	result = cannotFind;
	    	Builder alerBuidler = new Builder(this);
	    	 alerBuidler
				.setTitle(notFoundRecord)
				.setPositiveButton(continueSearch, null)
				.setNegativeButton(gotoRecording,new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						mDrawerLayout.closeDrawers();
					}
					
				});
	    	 alerBuidler.show();
	    }
	  }  

	

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

  //����
  	@Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  		// TODO Auto-generated method stub
  		int id = item.getItemId();
  		if (id == R.id.action_changeTheme) {
  			//��������	
  			if(themeFlag){
  				allbackground.setBackground(getResources().getDrawable(R.drawable.all_background2));
  				themeFlag = false;
  			}else{
  				allbackground.setBackground(getResources().getDrawable(R.drawable.all_background));
  				themeFlag = true;
  			}	
  					
  		}else if (id == R.id.action_changeLanguage){
  			//��������
  			Toast.makeText(getApplicationContext(), "changeLanguage", Toast.LENGTH_SHORT).show();
  			if(ok.equals("OK")){
  				inputName = inputNameDialog[1];
  				ok = okDialog[1];
  				cancel = cancelDialog[1];
  				save = saveDialog [1]; 
  				deleteWord = deleteWordDialog[1];
  				changeName = changeNameWord[1];	
  				text1.setText(text1Word[1]);
  				text3.setText(text3Word[1]);
  				languageFlag = "Chinese";
//  				 item1.setTitle("kkk");
  			}else{
  				inputName = inputNameDialog[0];
  				ok = okDialog[0];
  				cancel = cancelDialog[0];
  				save = saveDialog [0]; 
  				deleteWord = deleteWordDialog[0];
  				changeName = changeNameWord[0];	
  				text1.setText(text1Word[0]);
  				text3.setText(text3Word[0]);
  				languageFlag = "English";
  			}
  		}else if(id == R.id.action2){
  			selectFileLocation();
  		}else if (id == R.id.action_aboutUs){//��������
  			Toast.makeText(getApplicationContext(), "aboutme", Toast.LENGTH_SHORT).show();
  			Intent intent = new Intent(MainActivity.this,AboutUs.class);
  			intent.putExtra("language", languageFlag);
  			Toast.makeText(getApplicationContext(), "language :"+languageFlag, Toast.LENGTH_SHORT).show();
			startActivity(intent);
  		}
  		
  		return super.onOptionsItemSelected(item);
  	}

  	@Override
  	public boolean onPrepareOptionsMenu(Menu menu) {
  		// TODO Auto-generated method stub
  		//����settings�е���Ӣ��
  		if(ok.equals("ȷ��")){
  		    menu.findItem(R.id.action_changeTheme).setTitle(changeThemeWord[1]);
  		    menu.findItem(R.id.action_changeLanguage).setTitle(changeLanguageWord[1]);
  		    menu.findItem(R.id.action_aboutUs).setTitle(aboutUsString[1]);
  		    menu.findItem(R.id.action2).setTitle(changeSave[1]);
  		    
  		}else{
  			menu.findItem(R.id.action_changeTheme).setTitle(changeThemeWord[0]);
  		    menu.findItem(R.id.action_changeLanguage).setTitle(changeLanguageWord[0]);
  		    menu.findItem(R.id.action_aboutUs).setTitle(aboutUsString[0]);
  		    menu.findItem(R.id.action2).setTitle(changeSave[0]);
  		}
  		return super.onPrepareOptionsMenu(menu);
  		
  	}
  	public void selectBack()
	{
	//Code for selecting background
	}
	public void selectTheme()
	{
		
	}
	public void selectFileLocation()
	{
		Intent intent = new Intent(this, DirectoryPicker.class);
		intent.putExtra(DirectoryPicker.START_DIR, DirectoryPicker.ONLY_DIRS);
		startActivityForResult(intent, DirectoryPicker.PICK_DIRECTORY);

	}
	public String saveFileHelper(String a)
	{
		path = a;
		File files = new File(path);
		listFile_record = sortListFile(files.list());
		showRecord.notifyDataSetChanged();
		return path;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
		if(requestCode == DirectoryPicker.PICK_DIRECTORY && resultCode == RESULT_OK) 
		{
			//Testing Before
			Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
			Bundle extras = intent.getExtras();
			String savePath = (String) extras.get(DirectoryPicker.CHOSEN_DIRECTORY);
			if(savePath!=""){
				Toast.makeText(getApplicationContext(), "Path Saved!", Toast.LENGTH_SHORT).show();
				saveFileHelper(savePath);
				//Testing After
				Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
			}	
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Error Selecting Path :(", Toast.LENGTH_SHORT).show();
		}
	}
	//backstage running
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // ����HOME��
        if(keyCode == KeyEvent.KEYCODE_HOME){
            // ��ʾNotification
            notification.showNotification();
            moveTaskToBack(true);                
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	@Override
    protected void onStop() {
    // TODO Auto-generated method stub
		super.onStop();
		Log.v("BACKGROUND", "��������̨");
		notification.showNotification();
    }
}
