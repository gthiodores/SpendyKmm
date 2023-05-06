//
//  SpendingListView.swift
//  iosApp
//
//  Created by Lucy on 06/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SpendingListView: View {
    @ObservedObject var viewModel : SpendingListViewModel
    
    var body: some View {
        List {
            ForEach(viewModel.uiState.spendings, id: \.id.uuidString) { spending in
                VStack(alignment: .leading, spacing: 10.0) {
                    Text(spending.id.uuidString).lineLimit(1)
                    
                    Text("\(spending.amount)")
                }
            }
        }
        .navigationTitle("Spending list")
        .toolbar {
            ToolbarItem(placement: .navigation) { NavigationLink("Add", destination: SpendingAddView()) }
        }
    }
}
