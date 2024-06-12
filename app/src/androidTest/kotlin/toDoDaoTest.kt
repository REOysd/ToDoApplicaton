import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.websarva.wings.android.todoapp.database.createTemplate
import com.websarva.wings.android.todoapp.database.toDoDao
import com.websarva.wings.android.todoapp.database.toDoDatabase
import com.websarva.wings.android.todoapp.database.toDoEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class toDoDaoTest {
    private lateinit var toDo_Dao: toDoDao
    private lateinit var toDo_Database: toDoDatabase

    private var toDo_1 = toDoEntity(1,"title","description","2/3")
    private var toDo_2 = toDoEntity(2,"toDo","今日のすること","2/34")
    private var toDoTemplate = createTemplate(1,"template", "Text")

    @Before
    fun setup(){
        val context: Context = ApplicationProvider.getApplicationContext()
        toDo_Database = Room.inMemoryDatabaseBuilder(context, toDoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        toDo_Dao = toDo_Database.ToDoDao()
    }

    private suspend fun insertOne(){
        toDo_Dao.insert(toDo_1)
    }

    private suspend fun insertTwo(){
        toDo_Dao.insert(toDo_1)
        toDo_Dao.insert(toDo_2)
    }

    private suspend fun insertTemplate(){
        toDo_Dao.insertTemplate(toDoTemplate)
    }


    @Test
    fun insertTest() = runBlocking{
        insertOne()
        val all = toDo_Dao.getAll().first()
        assertEquals(all[0],toDo_1)
    }

    @Test
    fun insertTemplateTest() = runBlocking {
        insertTemplate()
        val all = toDo_Dao.getAllTemplate().first()
        assertEquals(all[0],toDoTemplate)
    }


    @Test
    fun deleteTest() = runBlocking {
        insertTwo()
        toDo_Dao.delete(toDo_1)
        toDo_Dao.delete(toDo_2)
        val all = toDo_Dao.getAll().first()
        assert(all.isEmpty())
    }

    @Test
    fun updataTest() = runBlocking {
        insertTwo()
        val updataToDo_1 = toDo_1.copy(id = 1, title = "update", description = "こんにちは")
        val updataToDo_2 = toDo_2.copy(id = 2, title = "update", time = "3/56")
        toDo_Dao.update(updataToDo_1)
        toDo_Dao.update(updataToDo_2)
        val all = toDo_Dao.getAll().first()
        assertEquals(all[0],updataToDo_1)
        assertEquals(all[1],updataToDo_2)

    }

    @After
    fun close(){
        toDo_Database.close()
    }
}