/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner


import org.springframework.format.annotation.DateTimeFormat
import org.springframework.samples.petclinic.visit.Visit
import java.util.*
import javax.persistence.*

/**
 * Simple business object representing a owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Antoine Rey
 */
@Entity
@Table(name = "pets")
data class Pet(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(name = "name")
        val name: String = "",

        @Column(name = "birth_date")
        @Temporal(TemporalType.DATE)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        val birthDate: Date? = null,

        @ManyToOne
        @JoinColumn(name = "type_id")
        val type: PetType? = null,

        @ManyToOne
        @JoinColumn(name = "owner_id")
        var owner: Owner? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "petId", fetch = FetchType.EAGER)
        val visits: MutableSet<Visit> = LinkedHashSet()

) {

    fun getVisits(): List<Visit> =
            visits.sortedWith(compareBy { it.date })

    fun addVisit(visit: Visit) {
        visits.add(visit)
        visit.petId = this.id
    }

    val isNew: Boolean
        get() = this.id == null

    override fun toString(): String =
            this.name

}