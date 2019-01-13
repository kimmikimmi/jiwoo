package com.jworkplace.demo.auth

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.jworkplace.demo.common.extention.getLogger
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.io.FileInputStream
import javax.annotation.PostConstruct

@Component
@Slf4j
class FirebaseAuthManager {
    val log = getLogger()

    @PostConstruct
    fun init() {
//        val serviceAccount = FileInputStream("tmp/serviceAccountKey.json")
        val serviceAccount = FileInputStream("tmp/diary_auth.json")


        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://abiding-ascent-227313.firebaseio.com")
                .setDatabaseUrl("https://diary-b3f7c.firebaseio.com")
                .build()

        FirebaseApp.initializeApp(options)
    }

    fun verifyToken(idToken: String): String {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)

        return decodedToken.uid
    }
}