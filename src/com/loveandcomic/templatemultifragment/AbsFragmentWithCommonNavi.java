package com.loveandcomic.templatemultifragment;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageButton;


/**
 * ページ下部共通ナビゲーション部分を持つフラグメントのための継承元クラス
 * このクラス自体は対応するフラグメントを持たないので抽象クラスとする
 *
 * @extends AbsFragmentWithCommonVariable 共通フィールドを作成する抽象メソッド（自作）
 */
public abstract class AbsFragmentWithCommonNavi extends AbsFragmentWithCommonVariable {

	///////////////////////////////////////////////////////////
	//フィールド
	///////////////////////////////////////////////////////////
	//継承されたフィールド（一例 詳しくは AbsFragmentWithCommonVariable 参照）
	//◆Activity my_activity //フラグメントの持ち主となるアクティビティ
	//◆Fragment my_fragment //フラグメント自身
	//◆Context  my_context  //持ち主のアクティビティのコンテキスト

	//フィールドの変数の使い方
	//外側のクラス（持ち主としてthisを付ける）：this.変数名
	//インナークラス（持ち主を指定しない）：変数名


	///////////////////////////////////////////////////////////
	//メソッド ライフサイクル順
	///////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル01：onAttach()
	 * フラグメントがアクティビティから最初に取り付けられた時に
	 * 呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル02：onCreate()
	 * システムがフラグメントを作成した時に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル03：onCreateView()
	 * フラグメントが画面描画をはじめて行ったタイミングで
	 * 呼び出される。
	 *
	 * 【重要】
	 * フラグメントを使用する時、クラスに記述するべき
	 * 中心的メソッド。
	 *
	 * inflater.inflate()で取得するViewを戻す。
	 * そのとき、リソースのレイアウトXMLを指定する。
	 *
	 * ※継承先で実装
	 */


	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル04：onActivityCreated()
	 * 呼び出し元になるActivityのonCreateメソッドが完了したら
	 * 呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル05：onViewStateRestored()
	 * フラグメントのビュー階層の状態が復元されるときに
	 * 呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル06：onStart()
	 * フラグメントがユーザーに見えるように生成された
	 * タイミングで呼び出される。
	 */
	@Override
	public void onStart() {
		super.onStart();

		/////////////////////////////////////////////////////////////////////////////////
		//ボタンクリックによるページ遷移のためのギミック
		//各種初期化

		//ハッシュマップ（連想配列のように使われる）
		HashMap<String, ImageButton> img_btns = new HashMap<String, ImageButton>();
		HashMap<String, Bitmap> bmps          = new HashMap<String, Bitmap>();
		HashMap<String, Bitmap> bmps_resized  = new HashMap<String, Bitmap>();

		//////////////////////////////////////////////////
		//イメージボタンインスタンスの作成
		//レイアウトでIDを割り振ったパーツをインスタンス化
		//HashMap「img_btns」に格納
		img_btns.put("home"     , (ImageButton)this.my_activity.findViewById(R.id.menu_button_home));
		img_btns.put("content01", (ImageButton)this.my_activity.findViewById(R.id.menu_button_content01));
		img_btns.put("content02", (ImageButton)this.my_activity.findViewById(R.id.menu_button_content02));
		img_btns.put("content03", (ImageButton)this.my_activity.findViewById(R.id.menu_button_content03));
		img_btns.put("content04", (ImageButton)this.my_activity.findViewById(R.id.menu_button_content04));

		//////////////////////////////////////////////////
		//各画像のビットマップインスタンスを用意する
		//リソース
		Resources resources = getResources();
		//HashMap「bmps」に格納
		bmps.put("home"     , BitmapFactory.decodeResource(resources, R.drawable.menu_button_home));
		bmps.put("content01", BitmapFactory.decodeResource(resources, R.drawable.menu_button_content01));
		bmps.put("content02", BitmapFactory.decodeResource(resources, R.drawable.menu_button_content02));
		bmps.put("content03", BitmapFactory.decodeResource(resources, R.drawable.menu_button_content03));
		bmps.put("content04", BitmapFactory.decodeResource(resources, R.drawable.menu_button_content04));


		//////////////////////////////////////////////////
		//各画像を適切な大きさにリサイズする
		//端末の画像サイズを取得
		WindowManager window_manager = this.my_activity.getWindowManager();
		Display display = window_manager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		//端末の幅と高さ
		int now_display_width_pixel  = metrics.widthPixels;
		int now_display_height_pixel = metrics.heightPixels;

		//画面が縦長の場合、ひとつのボタンの大きさは画面の幅の1/メニュー項目数
		//割り算をするときは少なくとも片方を少数を持ちえる型（doubleなど）にしないといけないが、
		//この後に使いたいcreateScaledBitmapが引数にintを求めるので、int型にキャストしている。
		int button_width = (int)(now_display_width_pixel / bmps.size());
		//画像の縦横比は横1：縦1.0
		int button_height = (int)(button_width * 1.0);


		//////////////////////////////////////////////////
		//ループ処理
		//foreach的な処理 HashMapの場合、「キーの文字列の一覧を取得」→「ループ内で各キーをhashmap.get("key")の中に入れる」というループになる
		//hashmap.keySet()で、キーの一覧が取得できる
		//HashMapでは、PHPの連想配列で言う「$value["key"]」を、「hashmap.get("key")」という表現で表す

		//みっつのHashMap「img_btns」「bmps」「bmps_resized」で共通のキーを使っているのでひとつのループ内でいろいろいっぺんに処理できる
		//◆ビットマップのリサイズ
		//◆ボタンとビットマップとのヒモ付
		//◆ボタンの余白をゼロにする
		//◆ボタンにイベントリスナーを設定する
		for(String key : img_btns.keySet()){
			//このループ内では「key」に「home」「content01」といったハッシュのキー文字列が格納されている

			//ビットマップを画面の大きさから算出したサイズにリサイズ
			Bitmap resized_bitmap = Bitmap.createScaledBitmap(
				bmps.get(key), //縮小する元のビットマップ
				button_width,  //幅
				button_height, //高さ
				false
			);

			//HashMap「bmps_resized」にリサイズした画像を追加
			bmps_resized.put(key, resized_bitmap);

			//ボタンとビットマップとのヒモ付け
			img_btns.get(key).setImageBitmap(bmps_resized.get(key));

			//ボタンの余白を0に
			img_btns.get(key).setPadding(0,0,0,0);

			//ボタンにイベントリスナーを追加
			//インスタンス.setイベントの種類Lisner(イベントリスナークラスのインスタンス（newしながら）)
			//どのボタンが押されたのかをクリックリスナーに通知するためにキー文字列を渡している。
			img_btns.get(key).setOnClickListener(new ChangeFragmentClickListener(this, key));

		}//ループ処理
		//////////////////////////////////////////////////

	}//function


	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル07：onResume()
	 * アクティビティがバックグラウンドからフォアグラウンドに
	 * 移るタイミングで呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル08：onPause()
	 * Activityがバックグラウンドに移ったか、もしくは
	 * アクティビティ内のフラグメントを変更する操作を行うことで
	 * ユーザーとの対話がされなくなった場合に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル09：onStop()
	 * アクティビティが停止したか、もしくはアクティビティ内の
	 * フラグメントを変更する操作を行うことでユーザーに表示され
	 * なくなった場合に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル10：onDestroyView()
	 * フラグメントのリソースをクリアする場合に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル11：onDestroy()
	 * フラグメントの状態が初期化される場合に呼び出される。
	 */

	///////////////////////////////////////////////////////////
	/**
	 * ライフサイクル12：onDetach()
	 * フラグメントがアクティビティから剥がされる直前に
	 * 呼び出される。
	 */


	///////////////////////////////////////////////////////////
	//メソッド 非ライフサイクル
	//抽象クラスの継承やインターフェイスの実装により
	//オーバーライドが義務化されるメソッドなど
	///////////////////////////////////////////////////////////
	/**
	 *
	 *
	 */

	///////////////////////////////////////////////////////////
	//クラス内クラス
	//タイマー処理などとの連携用
	///////////////////////////////////////////////////////////
	/**
	 *
	 *
	 */


}//class
