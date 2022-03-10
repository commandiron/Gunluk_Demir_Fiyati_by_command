package com.example.gunluk_demir_fiyati_by_command.core

import com.example.gunluk_demir_fiyati_by_command.R
import com.example.gunluk_demir_fiyati_by_command.presentation.navigation.BottomNavItem

sealed class CityPhotos(var cityUrlMap:Map<String,String>) {

    object Get: CityPhotos(
        hashMapOf(
            "Ankara" to "https://idsb.tmgrup.com.tr/ly/uploads/images/2022/02/08/180844.jpeg",
            "İstanbul" to "https://upload.wikimedia.org/wikipedia/commons/5/53/Bosphorus_Bridge_%28235499411%29.jpeg",
            "Karabük" to "https://www.yerelnet.org.tr/wp-content/uploads/2021/06/Karabuk-1.jpg",
            "Gebze" to "https://www.bushcraftokulu.com/wp-content/uploads/wpforo/attachments/2/173-gebze.jpg",
            "İskenderun" to "https://cdnuploads.aa.com.tr/uploads/Contents/2020/08/12/thumbs_b_c_6d9fffdcd440ab5cd6a2df113e59a200.jpg",
            "İzmir" to "https://www.egehaber.com/wp-content/uploads/2022/02/haftalik_illere_gore_vaka_sayisi_aciklandi_izmir_de_sevindiren_dusus_suruyor_h263928_cd80f.jpg",
            "Sivas" to "https://cdnuploads.aa.com.tr/uploads/Contents/2020/05/03/thumbs_b_c_973bb0de92898db175eff5fb2a0cbb9c.jpg",
            "Biga" to "https://gezicini.com/wp-content/uploads/2021/01/bigamerkez-1024x537.jpg",
            "Samsun" to "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Samsun_sea.jpg/1200px-Samsun_sea.jpg",
            "Kırıkkale" to "https://www.e-yasamrehberi.com/images/kirikkale.jpg",
            "Tekirdağ" to "https://trthaberstatic.cdn.wp.trt.com.tr/resimler/1612000/musilaj-aa-1612062_2.jpg",
            "Bilecik" to "https://www.yerelnet.org.tr/wp-content/uploads/2021/05/Bilecik.jpg")){

    }
}