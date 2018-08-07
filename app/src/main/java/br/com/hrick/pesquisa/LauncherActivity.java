package br.com.hrick.pesquisa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.sql.SQLException;

import br.com.hrick.estoquepessoal.api.UserApi;
import br.com.hrick.estoquepessoal.entity.User;
import br.com.hrick.estoquepessoal.exceptions.SqlExceptionCustom;
import br.com.hrick.estoquepessoal.repository.SharedPreferenceRepository;
import br.com.hrick.estoquepessoal.repository.UserRepository;
import br.com.hrick.estoquepessoal.utils.ApiUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LauncherActivity extends BaseActivity {

    SharedPreferenceRepository sharedPreferenceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        UserRepository.init(this);
        sharedPreferenceRepository = new SharedPreferenceRepository(this);
        carregar();
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.anim_splash);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.ivLogoSplash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }
    }

}
