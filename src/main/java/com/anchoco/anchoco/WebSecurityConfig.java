package com.anchoco.Anchoco;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          // アクセス権限の設定
          // staticディレクトリにある、'/css/','fonts','/js/'は制限なし
          .antMatchers("/css/**", "/fonts/**", "/js/**").permitAll()
          // '/admin/'で始まるURLには、'ADMIN'ロールのみアクセス可
          .antMatchers("/admin/**").hasRole("ADMIN")
          // 他は制限なし
          .anyRequest().authenticated()
        .and()
          // ログイン処理の設定
          .formLogin()
            // ログイン処理のURL
            .loginPage("/login")
            // usernameのパラメタ名
            .usernameParameter("username")
            // passwordのパラメタ名
            .passwordParameter("password")
            .permitAll()
        .and()
          // ログアウト処理の設定
          .logout()
            // ログアウト処理のURL
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            // ログアウト成功時の遷移先URL
            .logoutSuccessUrl("/login")
            // ログアウト時に削除するクッキー名
            .deleteCookies("JSESSIONID")
            // ログアウト時のセッション破棄を有効化
            .invalidateHttpSession(true)
            .permitAll()
        ;
    }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication()
      // ユーザ名'user', パスワード'user',ロール'USER'のユーザを追加
      .withUser("user").password("user").roles("USER")
      .and()
      // ユーザ名'admin', パスワード'admin',ロール'ADMIN'のユーザを追加
      .withUser("admin").password("admin").roles("ADMIN");
  }
}