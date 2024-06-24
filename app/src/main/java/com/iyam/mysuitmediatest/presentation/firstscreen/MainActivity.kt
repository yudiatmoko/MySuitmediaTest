package com.iyam.mysuitmediatest.presentation.firstscreen

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iyam.mysuitmediatest.R
import com.iyam.mysuitmediatest.databinding.ActivityMainBinding
import com.iyam.mysuitmediatest.presentation.secondscreen.SecondScreenActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnNext.setOnClickListener {
            setUserName()
            binding.etName.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        binding.btnCheck.setOnClickListener {
            checkPalindromeWord()
            binding.etPalindrome.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
    }

    private fun setUserName() {
        val name = binding.etName.text.toString().trim()
        if (isFormValid()) {
            mainViewModel.setUserName(name)
            navigateToSecondScreen(name)
        }
    }

    private fun checkPalindromeWord() {
        val words = binding.etPalindrome.text.toString().trim()
        if (isFormValid()) {
            if (isPalindrome(words)) {
                Toast.makeText(
                    this,
                    getString(R.string.is_palindrome),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.not_palindrome),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isFormValid(): Boolean {
        val name = binding.etName.text.toString().trim()
        val words = binding.etPalindrome.text.toString().trim()

        return checkNameValidation(name) && checkWordsValidation(words)
    }

    private fun checkWordsValidation(words: String): Boolean {
        return if (words.isEmpty()) {
            binding.tilPalindrome.isErrorEnabled = true
            binding.tilPalindrome.error = getString(R.string.this_field_is_required)
            false
        } else {
            binding.tilPalindrome.isErrorEnabled = false
            true
        }
    }

    private fun checkNameValidation(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.tilName.isErrorEnabled = true
            binding.tilName.error = getString(R.string.this_field_is_required)
            false
        } else {
            binding.tilName.isErrorEnabled = false
            true
        }
    }

    private fun isPalindrome(words: String) = words.filter { it.isLetterOrDigit() }.run {
        equals(reversed(), true)
    }

    private fun navigateToSecondScreen(name: String) {
        SecondScreenActivity.startActivity(this, name)
    }
}
