package id.dimas.kpu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.dimas.kpu.Extension.loadImage
import id.dimas.kpu.databinding.ActivityDetailBinding
import id.dimas.kpu.repo.VotersRepo

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val votersRepo: VotersRepo by lazy { VotersRepo(this@DetailActivity) }

    private val viewModel: ListViewModel by lazy { ListViewModel(votersRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeVoter()
    }


    private fun observeVoter() {

        val nik = intent.getStringExtra("nik")
        if (nik != null) {
            viewModel.getVoterBynik(nik)
        }
        viewModel.votersNIK.observe(this) { result ->
            binding.apply {
                tvNik.text = result.nik
                tvName.text = result.nama
                tvPhone.text = result.noHp
                tvGender.text = result.jenisKelamin
                tvDate.text = result.tglPendataan
                tvLocation.text = result.location
                ivImage.loadImage(result.img)
            }

        }

    }
}