package app.tracktion.data.repositories

import app.tracktion.domain.daos.CategoryDAO
import app.tracktion.domain.interfaces.Repository
import app.tracktion.domain.models.Category
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryRepository : Repository<Category> {
    override suspend fun findById(id: Int) = transaction {
        getDAOById(id)?.toModel()
    }


    override suspend fun getAll() = transaction {
        CategoryDAO.all().map { it.toModel() }
    }

    override suspend fun delete(id: Int) = transaction {
        getDAOById(id)?.let {
            it.delete()
            true
        } ?: false
    }

    override suspend fun add(entry: Category) = transaction {
        CategoryDAO.new {
            name = entry.name
            imageUrl = entry.imageUrl
        }.toModel()
    }

    override suspend fun update(entry: Category) = transaction {
        val category = getDAOById(entry.id)

        category?.name = entry.name
        category?.imageUrl = entry.imageUrl

        category?.toModel()
    }

    private fun getDAOById(id: Int) = CategoryDAO.findById(id)
}