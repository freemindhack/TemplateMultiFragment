package com.loveandcomic.templatemultifragment;

import java.util.logging.Logger;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * HOME画面のためのメインアクティビティ
 */
public class ActivityMain extends Activity{
	///////////////////////////////////////////////////////////
	//フィールド
	///////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////
	//メソッド ライフサイクル順
	///////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル01：onCreate()
	 * 最初に呼び出される。初期化処理を行う
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setContentView()メソッド：引数で渡した部品がこのアクティビティの画面として表示される
		//「R」というのは「res」フォルダのこと。「R.layout.activity_main」で、「resフォルダのlayoutフォルダのactivity_main.xmlファイル」であることがわかる
		setContentView(R.layout.activity_main);
		//（おそらく、表示すべきフラグメントがまだ生成されていなければ、ということ）
		if (savedInstanceState == null) {

			//////////////////////////////////////////////////////////////
			//トランザクションによるフラグメントの初期表示設定

			//フラグメントマネジャーインスタンス
			FragmentManager fragment_manager = getFragmentManager();
			//FragmentTransactionインスタンスの作成
			FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

			//初期表示のためにフラグメントクラスをADD
			//この「R.id.container」は、このアクティビティのためのレイアウトXMLにある要素に振られたID
			//newされているのは、Fragmentを継承した、フラグメントのためのクラス。
			////初期設定で作らせるとこれはクラス内クラスとして作成されるが、ファイルでクラス分けするとわかりやすい。
			fragment_transaction.add(R.id.container, new FragmentMain());

			//commit()で表示
			fragment_transaction.commit();
		}//if

		//ロガー
		final Logger logger = Logger.getLogger("com.loveandcomic.templatemultifragment");
		logger.info("ロガー起動 ActivityMain");

	}//function


	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル02：onRestart()
	 * Activityの停止後、再開する直前に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル03：onStart()
	 * 画面が表示される直前に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル04：onResume()
	 * ユーザーからの入力が可能となる直前に呼び出される。
	 */


	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル05：onPause()
	 * Activityから抜けようとした時に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル06：onStop()
	 * Activityが非表示になった時に呼び出される
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル07：onDestroy()
	 * Activityが破棄される時に呼び出される
	 */


	///////////////////////////////////////////////////////////
	//メソッド 非ライフサイクル
	//抽象クラスの継承やインターフェイスの実装により
	//オーバーライドが義務化されるメソッドなど
	///////////////////////////////////////////////////////////
	/**
	 * オプションメニューのためのメソッド
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//メニューXMLの指定：共通のファイル（menu_common.xml）を使う
		getMenuInflater().inflate(R.menu.menu_common, menu);
		return true;
	}//function

	/**
	 * オプションメニューのためのメソッド
	 * 選択された項目を通知する
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//このブロックで使う変数の初期化
		//このメソッドの戻り値であるboolean
		boolean result;

		//タップされたメニューのID
		int item_id = item.getItemId();

		//フラグメントマネジャーインスタンス
		FragmentManager fragment_manager = this.getFragmentManager();
		//フラグメント入れ替えのためのトランザクションインスタンス
		FragmentTransaction fragment_transaction = fragment_manager.beginTransaction();

		switch (item_id){

			//「ホーム」メニュー
			case R.id.menu_home:
				//ホーム画面フラグメントに入れ替え
				fragment_transaction.replace(R.id.container, new FragmentMain());

				result = true;
				break;

			//「ヘルプ」メニュー
			case R.id.menu_help:
				//ヘルプ画面フラグメントに入れ替え
				fragment_transaction.replace(R.id.container, new FragmentHelp());
				result = true;
				break;

			//「このアプリについて」メニュー
			case R.id.menu_info:
				//このアプリについて画面フラグメントに入れ替え
				fragment_transaction.replace(R.id.container, new FragmentInfo());
				result = true;
				break;

			//default
			default:
				result = onOptionsItemSelected(item);
				break;
		}//switch

		fragment_transaction.addToBackStack(null);
		//入れ替え実行
		fragment_transaction.commit();

		//bool値を戻す
		return result;

	}//function


	///////////////////////////////////////////////////////////
	//クラス内クラス
	//タイマー処理やフラグメントなどとの連携用
	///////////////////////////////////////////////////////////
	/**
	 *
	 */

}//class
