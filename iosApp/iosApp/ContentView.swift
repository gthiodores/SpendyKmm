import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        NavigationView {
            SpendingListView(
                viewModel: SpendingListViewModel(presenter: SpendingListPresenterHelper().get())
            )
        }
        .navigationViewStyle(.stack)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
