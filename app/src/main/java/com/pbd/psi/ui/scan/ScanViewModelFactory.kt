import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pbd.psi.repository.ScanRepository
import com.pbd.psi.ui.scan.ScanViewModel

class ScanViewModelFactory(private val repository: ScanRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScanViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
