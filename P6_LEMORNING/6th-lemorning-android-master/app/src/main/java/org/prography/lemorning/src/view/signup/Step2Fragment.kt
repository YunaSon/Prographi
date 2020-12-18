package org.prography.lemorning.src.view.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.prography.lemorning.BaseFragment
import org.prography.lemorning.R
import org.prography.lemorning.config.Constants.DATE_FORMAT_YYYY_MM_DD
import org.prography.lemorning.databinding.FragmentStep2SignUpBinding
import org.prography.lemorning.src.viewmodel.SignUpViewModel
import org.prography.lemorning.utils.base.BaseEvent
import org.prography.lemorning.utils.components.TimePickerSliderBuilder
import java.util.*


class Step2Fragment(override val layoutId: Int = R.layout.fragment_step2_sign_up)
    : BaseFragment<FragmentStep2SignUpBinding, SignUpViewModel>() {

    companion object {
        private const val REQUEST_CODE_CHOOSE = 10
        private const val REQUEST_CODE_CROP = 11
        private const val REQUEST_STORAGE_PERMISSION = 12
    }

    override fun getViewModel(): SignUpViewModel
            = ViewModelProvider(requireActivity()).get(SignUpViewModel::class.java)

    override fun initView() {

        /* Set On Click Listener */
        binding.ivProfileStep2SignUp.setOnClickListener { openImagePicker() }
        binding.ivChangeProfileStep2SignUp.setOnClickListener { openImagePicker() }
        binding.tvBirthStep2SignUp.setOnClickListener { openDatePickerSlider() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CHOOSE -> if (resultCode == Activity.RESULT_OK) cropImage(data?.data)
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    viewmodel.profileUri.value = result.uri
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    result.error.printStackTrace()
                    showToast("이미지 등록에 실패하였습니다.")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            var check = true
            for (i in grantResults) check = check && i == PackageManager.PERMISSION_GRANTED
            if (check) openImagePicker() else viewmodel.navTo.value = BaseEvent(-1)
        }
    }

    private fun checkPermissions() : Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showToast("해당 기능을 사용하려면 카메라와 외부저장소 권한이 필요합니다.")
            } else {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE_PERMISSION)
            }
            return false
        }
        return true
    }

    private fun openImagePicker() {
        if (!checkPermissions()) return
        startActivityForResult(Intent.createChooser(Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }, "Select Picture"), REQUEST_CODE_CHOOSE)
    }

    private fun cropImage(uri : Uri?) {
        uri?.let {
            if (!checkPermissions()) return
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(requireActivity(), this)
        }
    }

    private fun openDatePickerSlider() {
        val calendar = Calendar.getInstance()
        viewmodel.birth.value?.let {
            calendar.set(it.substring(0, 4).toInt(), it.substring(5, 7).toInt(), it.substring(8).toInt())
            calendar.add(Calendar.MONTH, -1)
        }
        val pickerSliderBuilder: TimePickerSliderBuilder =
            TimePickerSliderBuilder(requireActivity(), OnTimeSelectListener { date, _ -> viewmodel.birth.value = DATE_FORMAT_YYYY_MM_DD.format(date) })
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.confirm))
                .setDate(calendar)
                .setLabel("", "", "", "", "", "")
                .setTitleText("생년월일")
                .setTitleSize(14)
                .setContentTextSize(22)
                .setSubCalSize(14)
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setRangDate(Calendar.getInstance().apply { set(1920, 1, 1) }, Calendar.getInstance())
        pickerSliderBuilder.build().show()
    }
}