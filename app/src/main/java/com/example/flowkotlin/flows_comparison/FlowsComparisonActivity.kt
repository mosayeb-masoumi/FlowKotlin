package com.example.flowkotlin.flows_comparison

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowkotlin.R
import com.example.flowkotlin.databinding.ActivityFlowsComparisonBinding
import com.example.flowkotlin.flow_operators.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowsComparisonActivity : AppCompatActivity() {

    lateinit var binding: ActivityFlowsComparisonBinding

    val viewModel : ComparisonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlowsComparisonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLiveData.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }




        binding.btnFlow.setOnClickListener {
            /********* flow  after rotation the data will be lost ******/
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.txtFlow.text = it.toString()
                }
            }

        }



        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }



        subscribeToObservables()


    }

    /*** in live data if we rotate the screen the data won,t lost ***/
    private fun subscribeToObservables() {


        /************** liveData Observation ********/
      // in fragment  viewModel.liveData.observe(viewLifecycleOwner)
        viewModel.liveData.observe(this){
            binding.txtLiveData.text = it

//            Snackbar.make(binding.root , it , Snackbar.LENGTH_LONG).show()
        }


        /** stateFlow is as same as liveData and save the data while rotation ***/
        /************** stateFlow Observation ********/
        /** in stateFlow we should use launchWhenCreated **/
        lifecycleScope.launchWhenCreated {
            viewModel.stateFlow.collectLatest {
                binding.txtStateFlow.text = it


                /** in stateFlow everyTime screen rotate ,here will be called and it's not good for showing for example snackbar
                 * because after every rotation snackbar will be shown thats why we use sharedFlow for such tasks*/

                /** stateFlow emit value every time when screen rotate. but if screen not rotate and we press the button
                 * again the value would not emit the same behavior as liveData */

//                Snackbar.make(binding.root , it , Snackbar.LENGTH_LONG).show()

            }
        }








        /** shared flow doesnt keep last data same as liveData and stateFlow and does not trig every rotation . just trig once
         * and a good option for using to show snackbar*/

        /*** by pressing button every time emit will happen ***/
        /** good to call api and for navigation to change fragments **/
        lifecycleScope.launchWhenCreated {
            viewModel.sharedFlow.collectLatest {
                binding.txtSharedFlow.text = it
                Snackbar.make(binding.root , it , Snackbar.LENGTH_LONG).show()

            }
        }
    }



}