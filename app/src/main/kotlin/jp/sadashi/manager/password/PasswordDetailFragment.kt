package jp.sadashi.manager.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.sadashi.manager.password.databinding.FragmentPasswordDetailBinding

class PasswordDetailFragment : Fragment() {

    private val args: PasswordDetailFragmentArgs by navArgs()

    private var _binding: FragmentPasswordDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewSecond.text = "${args.id}"

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_PasswordDetailFragment_pop)
        }
    }
}
