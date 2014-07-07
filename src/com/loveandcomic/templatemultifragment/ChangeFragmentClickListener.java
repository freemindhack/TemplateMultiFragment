package com.loveandcomic.templatemultifragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 各ページ下部メニューバーによるフラグメント入れ替えギミックのための共通クラス
 * クリックリスナークラス
 */
public class ChangeFragmentClickListener implements OnClickListener {
	///////////////////////////////////////////////////////////
	//フィールド
	///////////////////////////////////////////////////////////
	//フラグメント入れ替えギミックのためのフラグメントトランザクションインスタンス
	private Fragment including_fragment;  //共通メニューバー部分をインクルードしているフラグメント
	private String string_clicked_button; //クリックされたボタンがどれなのかを示す文字列

	//コンストラクタ
	public ChangeFragmentClickListener(Fragment fragment, String string){
		this.including_fragment    = fragment;
		this.string_clicked_button = string;
	}

	@Override
	public void onClick(View view) {
		//この引数「view」はクリックされたビュー要素のこと 作成したイメージボタンのインスタンスが来る

		//フラグメント入れ替えのためのフラグメントトランザクションインスタンスを取得
		FragmentManager fragment_manager = this.including_fragment.getFragmentManager(); //フラグメントマネジャーインスタンス
		FragmentTransaction transaction = fragment_manager.beginTransaction();           //フラグメントトランザクションインスタンス
		transaction.replace(R.id.container, new FragmentMain());                         //表示インスタンスの初期化

		//各表示遷移先とその方法とを割り当て
		if(this.string_clicked_button.equals("home")){
			//Javaでは文字列オブジェクトの比較は「==」ではなく「equals("")」を使う
			transaction.replace(R.id.container, new FragmentMain());
		}

		if(this.string_clicked_button.equals("content01")){
			transaction.replace(R.id.container, new FragmentContent01());
		}

		if(this.string_clicked_button.equals("content02")){
			transaction.replace(R.id.container, new FragmentContent02());
		}

		if(this.string_clicked_button.equals("content03")){
			transaction.replace(R.id.container, new FragmentContent03());
		}

		if(this.string_clicked_button.equals("content04")){
			transaction.replace(R.id.container, new FragmentContent04());
		}

		//フラグメント入れ替え実行
		transaction.addToBackStack(null);
		transaction.commit();

	}//function


}//class
