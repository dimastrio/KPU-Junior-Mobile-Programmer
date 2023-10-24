package id.dimas.kpu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.dimas.kpu.adapter.VoterAdapter
import id.dimas.kpu.databinding.ActivityViewBinding
import id.dimas.kpu.model.Voters
import id.dimas.kpu.repo.VotersRepo

class ViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBinding

    private val votersAdapter by lazy {
        VoterAdapter(::onClickItem)
    }

    private val votersRepo: VotersRepo by lazy { VotersRepo(this@ViewActivity) }

    private val viewModel: ListViewModel by lazy { ListViewModel(votersRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getAllVoter()
        setUpRecyclerview()
    }


    private fun setUpRecyclerview() {
        binding.rvVoters.adapter = votersAdapter
        binding.rvVoters.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        observeVoters()
    }

    private fun observeVoters() {
        viewModel.voters.observe(this) {
            votersAdapter.submitList(it)
        }
    }

    private fun onClickItem(voters: Voters) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("nik", voters.nik)
        startActivity(intent)
    }
}