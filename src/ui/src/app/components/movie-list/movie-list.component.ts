import { Component, OnInit } from '@angular/core';
import { Movie } from 'src/app/models/movie';
import { MovieList } from 'src/app/models/movie-list';
import { MovieService } from 'src/app/services/movie.service';
import { SpinnerService } from 'src/app/services/spinner.service';
import { Genre } from 'src/app/models/genre';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.scss']
})
export class MovieListComponent implements OnInit {

  private readonly movieService: MovieService;
  public page: number = 0;
  public totalPage: number = 1;
  private spinner: SpinnerService;
  public error: string = '';
  private sourceMovieList: Array<Movie> = new Array<Movie>();
  public movieList: Array<Movie> = new Array<Movie>();
  public posterBaseUrl: string = MovieService.IMAGE_URL;
  public hideBranding: boolean = true;
  public showModal: boolean = false;
  public posterImage: string = '';
  public maxRecords: number = 5;
  private loaded: number = 0;

  constructor(movieSerivce: MovieService, spinner: SpinnerService) {
    this.movieService = movieSerivce;
    this.spinner = spinner;
    this.spinner.show();
  }

  ngOnInit(): void {
    this.loadMovies();
  }

  public loadMoreMovies() {
    if ((this.sourceMovieList && this.sourceMovieList.length > 0) && (this.loaded < this.sourceMovieList.length)) {
      const start: number = (this.loaded + 1);
      const end = (start + this.maxRecords);
      if (end >= this.sourceMovieList.length) {
        this.spinner.show();
        this.loadMovies();
      } else {
        this.spinner.show();
        for (let i = start; i < end; i++) {
          this.loaded++;
          this.loadGenre(this.sourceMovieList[i], (i < end));
        }
      }
    } else if (this.sourceMovieList && this.sourceMovieList.length <= this.loaded && this.page < this.totalPage) {
      this.spinner.show();
      this.loadMovies();
    }
  }

  public loadMovies() {
    const currentPage = this.page + 1;
    this.movieService.fetch(currentPage).subscribe({
      next: (list: MovieList) => {
        this.page = list.page;
        this.totalPage = list.total_pages;
        if (list.results.length > 0) {
          this.sourceMovieList = this.sourceMovieList.concat(list.results);
          for (let i = 0; i < this.maxRecords; i++) {
            this.loaded++;
            this.loadGenre(list.results[i]);
          }
        }
        this.spinner.hide();
      },
      error: err => {
        this.error = err.error.message;
        this.spinner.hide();
      }
    });
  }

  private loadGenre(movie: Movie, hideSpinner:boolean = false): void {
    if (!movie || !movie.id) {
      return;
    }
    const genreSet = new Set<string>();
     this.movieService.fetchGenre(movie.id).subscribe({
      next: (list) => {
        if (list.genres.length > 0) {
          list.genres.forEach((genre: Genre) => {
            genreSet.add(genre.name);
          });
          movie.genre = Array.from(genreSet).join('<br />');
          this.movieList.push(movie);
          if (hideSpinner) {
            this.spinner.hide();
          }
        }
      },
      error: err => {
        this.error = err.error.message;
        this.spinner.hide();
      }
    });
  }

  public showModalDialog(posterPath: string){
    this.posterImage = `${this.posterBaseUrl}/${posterPath}`;
    this.showModal = true;
  }

  public hideModalDialog(){
    this.posterImage = ``;
    this.showModal = false;
  }

}
