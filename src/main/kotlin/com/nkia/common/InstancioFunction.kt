import org.instancio.Select
import org.instancio.TargetSelector
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField

// 최상위 함수로 정의
fun <T, V> field(property: KProperty1<T, V>): TargetSelector {
    val field = property.javaField!!
    return Select.field(field.declaringClass, field.name)
}